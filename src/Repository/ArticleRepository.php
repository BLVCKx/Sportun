<?php

namespace App\Repository;

use App\Entity\Article;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Article|null find($id, $lockMode = null, $lockVersion = null)
 * @method Article|null findOneBy(array $criteria, array $orderBy = null)
 * @method Article[]    findAll()
 * @method Article[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ArticleRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Article::class);
    }

    // /**
    //  * @return Article[] Returns an array of Article objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('a')
            ->andWhere('a.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('a.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Article
    {
        return $this->createQueryBuilder('a')
            ->andWhere('a.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
    public function listArticleById($id){
        $entityManager=$this->getEntityManager();
        $query=$entityManager
            ->createQuery("SELECT a as moyenne FROM APP\Entity\Commentaire a JOIN c.article a WHERE a.id=:id ")
            ->setParameter('name',$id);
        return $query->getSingleScalarResult();
    }
    public function findById($id){
        $entityManager=$this->getEntityManager();
        $query=$entityManager
                ->createQuery("SELECT a. as moyenne FROM APP\Entity\Commentaire a JOIN a.article a WHERE a.id=:id ")
            ->setParameter('id',$id);
        return $query->getSingleScalarResult();
    }
    public function findBySlug($slug){
        $entityManager=$this->getEntityManager();
        $query=$entityManager
            ->createQuery("SELECT a. as moyenne FROM APP\Entity\Commentaire a JOIN a.article a WHERE a.slug=:slug ")
            ->setParameter('slug',$slug);
        return $query->getSingleScalarResult();
    }
    public function searchArticle($str){
        return $this->createQueryBuilder('article')
            ->where('article.titre LIKE :nsc OR article.decription LIKE :nsc OR article.text LIKE :nsc OR article.tag LIKE :nsc')
            ->setParameter('nsc', '%'.$str.'%')
            ->getQuery()
            ->getResult();
    }
}
