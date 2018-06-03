package com.example.jose5.a35mm.modelo;

/**
 * Created by jose5 on 6/3/2018.
 */

public class User {
    private String Nombre;
    private int id;
    private boolean isAdmin=false;

    public User(String nombre, int id) {
        Nombre = nombre;
        this.id = id;
    }

}