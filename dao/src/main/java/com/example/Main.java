package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class Main {

    public static void main(String[] args)  throws Exception
    {
        Schema schema = new Schema(1, "com.example.xavier.lab_viajesbuses.dao");
        schema.enableKeepSectionsByDefault();
        createDatabase(schema);
        DaoGenerator generator =  new DaoGenerator();
        generator.generateAll(schema, args[0]); //Folder con clases se envia como arg en el gradle.build
    }

    private static void createDatabase(Schema schema) {
        Entity buses = schema.addEntity("buses");
        buses.addStringProperty("matricula").primaryKey();
        buses.addStringProperty("marca");
        buses.addStringProperty("modelo");

        Entity viajes = schema.addEntity("viajes");
        viajes.addIdProperty(); //ID del viaje, autoincremental y primary key
        Property busIDProperty = viajes.addStringProperty("matricula_bus").getProperty();
        viajes.addToOne(buses, busIDProperty); //llave foranea de buses
        viajes.addStringProperty("destino");
        viajes.addStringProperty("pasajeros");
    }
}
