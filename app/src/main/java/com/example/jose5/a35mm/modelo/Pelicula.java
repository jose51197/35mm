package com.example.jose5.a35mm.modelo;

/**
 * Created by jose5 on 6/3/2018.
 */

import android.media.Image;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by jose5 on 5/26/2018.
 */

public class Pelicula {
    private String nombre;
    private int calificacion;
    private String foto="https://images-na.ssl-images-amazon.com/images/I/51n%2BW2kt8DL.jpg";
    private String descripcion;
    private ArrayList<String> comentarios;
    private ArrayList<String> Actores;
    private ArrayList<String> Directores;

    public Pelicula(String nombre, int calificacion, String foto, String descripcion, ArrayList<String> comentarios, ArrayList<String> actores, ArrayList<String> directores) {
        this.nombre = nombre;
        this.calificacion = calificacion;
        this.foto = foto;
        this.descripcion = descripcion;
        this.comentarios = comentarios;
        Actores = actores;
        Directores = directores;
    }
}
