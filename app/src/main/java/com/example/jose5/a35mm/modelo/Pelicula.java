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
    private String foto;
    private String descripcion;
    private int anhio;
    private ArrayList<String> comentarios;
    private ArrayList<String> Actores;
    private ArrayList<String> Directores;

    public Pelicula() {
    }

    public Pelicula(String nombre, int calificacion, String foto, String descripcion, ArrayList<String> comentarios, ArrayList<String> actores, ArrayList<String> directores) {
        this.nombre = nombre;
        this.calificacion = calificacion;
        this.foto = foto;
        this.descripcion = descripcion;
        this.comentarios = comentarios;
        Actores = actores;
        Directores = directores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<String> comentarios) {
        this.comentarios = comentarios;
    }

    public ArrayList<String> getActores() {
        return Actores;
    }

    public void setActores(ArrayList<String> actores) {
        Actores = actores;
    }

    public ArrayList<String> getDirectores() {
        return Directores;
    }

    public void setDirectores(ArrayList<String> directores) {
        Directores = directores;
    }

    public int getAnhio() {
        return anhio;
    }

    public void setAnhio(int anhio) {
        this.anhio = anhio;
    }
}
