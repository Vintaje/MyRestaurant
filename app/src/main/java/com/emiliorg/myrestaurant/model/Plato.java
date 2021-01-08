package com.emiliorg.myrestaurant.model;

import java.util.Arrays;

public class Plato {
    private int id_plato;
    private String name;
    private Ingredientes ingredientes;
    private float precio;
    private String image;

    public Plato(){

    }

    public Plato(int id_plato, String name, Ingredientes ingredients, float price) {
        this.id_plato = id_plato;
        this.name = name;
        this.ingredientes = ingredients;
        this.precio = price;

    }

    public Plato(int id_plato, String name, Ingredientes ingredients, float price, String urlPhoto) {
        this.id_plato = id_plato;
        this.name = name;
        this.ingredientes = ingredients;
        this.precio = price;

        this.image = urlPhoto;
    }

    public int getId_plato() {
        return id_plato;
    }

    public void setId_plato(int id_plato) {
        this.id_plato = id_plato;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredientes getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Ingredientes ingredientes) {
        this.ingredientes = ingredientes;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    @Override
    public String toString() {
        return "Plato{" +
                "id_plato=" + id_plato +
                ", name='" + name + '\'' +
                ", ingredientes=" + ingredientes.toString() +
                ", precio=" + precio +
                ", image='" + image + '\'' +
                '}';
    }
}
