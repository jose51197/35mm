package com.example.jose5.a35mm.modelo;

/**
 * Created by jose5 on 6/3/2018.
 */

public class User {
    private String Nombre;
    private String id;

    public User(String nombre, String id) {
        Nombre = nombre;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}