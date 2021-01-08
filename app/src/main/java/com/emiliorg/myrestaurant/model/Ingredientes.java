package com.emiliorg.myrestaurant.model;

public class Ingredientes {
    private boolean vegano;
    private boolean gluten;
    private boolean lactosa;
    private boolean picante;

    public Ingredientes() {
    }

    public Ingredientes(boolean vegano, boolean gluten, boolean lactosa, boolean picante) {
        this.vegano = vegano;
        this.gluten = gluten;
        this.lactosa = lactosa;
        this.picante = picante;
    }

    public boolean isVegano() {
        return vegano;
    }

    public void setVegano(boolean vegano) {
        this.vegano = vegano;
    }

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }

    public boolean isLactosa() {
        return lactosa;
    }

    public void setLactosa(boolean lactosa) {
        this.lactosa = lactosa;
    }

    public boolean isPicante() {
        return picante;
    }

    public void setPicante(boolean picante) {
        this.picante = picante;
    }

    @Override
    public String toString() {
        return "Ingredientes{" +
                "vegano=" + vegano +
                ", gluten=" + gluten +
                ", lactosa=" + lactosa +
                ", picante=" + picante +
                '}';
    }
}
