package com.example.servingwebcontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ServingWebContentApplication {



    @Autowired
    EtudiantRepository etudiantRepository;

    public static void main(String[] args) {
            SpringApplication.run(ServingWebContentApplication.class, args);

            ArrayList<Etudiant> list =new ArrayList<Etudiant>();//Creating arraylist
                Etudiant fabien = new Etudiant();
                fabien.setPrenom("Fabien");
                fabien.setNom("Chapeau");
                fabien.setAge(36);
                fabien.setSexe("H");
                fabien.setPhotoUrl("fab.jpg");
                list.add(fabien);


            //Printing the arraylist object
            System.out.println(list);
        }
        }



