package com.example.jose5.a35mm.modelo;

/**
 * Created by jose5 on 6/4/2018.
 */

public class Genero {
    String nombre;
    String id;

    public Genero(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getId() {
        return id;
    }
}
