/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Style;
import java.io.IOException;
import java.util.List;
import com.codename1.ui.Container;
import com.codename1.googlemaps.MapContainer;
import com.codename1.maps.layers.PointLayer;
import com.mycompany.entites.Location;
import com.mycompany.entites.Services;
import com.mycompany.services.ServiceC;
import java.util.ArrayList;

/**
 *
 * @author BLVCK
 */
public class MapForm {

    Form f = new Form();
    MapContainer cnt = null;

    public MapForm() {

        try {
            cnt = new MapContainer("AIzaSyCoisQH2BitXRAjnsvOpse64eygC_JRQ-g");
        } catch (Exception ex)                                                                                   {
            ex.printStackTrace();
        }

        Button btnMoveCamera = new Button("Mon Pays");
        btnMoveCamera.addActionListener(e -> {
            cnt.setCameraPosition(new Coord(36.8189700, 10.1657900));
        });
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(10));

        

            
            /*ArrayList<Services> list = ServiceC.getInstance().affichageServices();
            for (Services rec : list){
                Location loc = rec.getLocation();
                int lat = (int) loc.getLat();
                int lng = (int) loc.getLng();
               System.out.println("lat"+lat);
 
            }*/
             
    
            
                cnt.clearMapLayers();
                ArrayList<Services> list = ServiceC.getInstance().affichageServices();
                  for (Services rec : list){
                Location loc = rec.getLocation();
                float lat = (float)loc.getLat();
                float lng =   (float)loc.getLng(); 
              
                System.out.print("lat"+lat);
                System.out.print("lng"+lng);
                
              
Coord coord = new Coord(loc.getLat(),loc.getLng());
cnt.setCameraPosition(coord);
cnt.addMarker(EncodedImage.createFromImage(markerImg,false), cnt.getCameraPosition(), "Text", "Text", null);
               /*Coord lastLocation = new Coord(loc.getLat(), loc.getLng());
                cnt.addMarker(markerImg,lastLocation);*/
               /*cnt.addMarker(
                        EncodedImage.createFromImage(markerImg, false),
                        cnt.getCoordAtPosition(36,10),
                        ""+cnt.getCameraPosition().toString(),
                        "",
                        e3->{
                                ToastBar.showMessage("You clicked "+cnt.getName(), FontImage.MATERIAL_PLACE);
                        }
                );*/}
            ConnectionRequest r = new ConnectionRequest();
            r.setPost(false);
            r.setUrl("http://maps.google.com/maps/api/geocode/json?latlng=" + cnt.getCameraPosition().getLatitude() + "," + cnt.getCameraPosition().getLongitude() + "&oe=utf8&sensor=false");
            NetworkManager.getInstance().addToQueueAndWait(r);

            JSONParser jsonp = new JSONParser();
            try {
                java.util.Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(r.getResponseData()).toCharArray()));
                System.out.println("roooooot:" + tasks.get("results"));
                List<java.util.Map<String, Object>> list1 = (List<java.util.Map<String, Object>>) tasks.get("results");
                /*java.util.Map<String, Object> list = (java.util.Map<String, Object>) list1.get(0);

                List<java.util.Map<String, Object>> listf = (List<java.util.Map<String, Object>>) list.get("address_components");
                String ch = "";
                for (java.util.Map<String, Object> obj : listf) {
                    ch = ch + obj.get("long_name").toString();
                }

                //b.setAdresse(ch);*/
            } catch (IOException ex) {
            }

         
        Container root = new Container();
        f.setLayout(new BorderLayout());
        f.addComponent(BorderLayout.CENTER, cnt);
        f.addComponent(BorderLayout.SOUTH, btnMoveCamera);
        f.show();
        //f.getToolbar().addCommandToRightBar("back", null, (ev)->{ new AjoutReclamationForm(f).show()});

    }

}
