package com.emiliorg.myrestaurant.model

class Ingredientes {
    private var vegano = false
    private var gluten = false
    private var lactosa = false
    private var picante = false

    constructor() {}
    constructor(vegano: Boolean, gluten: Boolean, lactosa: Boolean, picante: Boolean) {
        this.vegano = vegano
        this.gluten = gluten
        this.lactosa = lactosa
        this.picante = picante
    }

    fun isVegano(): Boolean {
        return vegano
    }

    fun setVegano(vegano: Boolean) {
        this.vegano = vegano
    }

    fun isGluten(): Boolean {
        return gluten
    }

    fun setGluten(gluten: Boolean) {
        this.gluten = gluten
    }

    fun isLactosa(): Boolean {
        return lactosa
    }

    fun setLactosa(lactosa: Boolean) {
        this.lactosa = lactosa
    }

    fun isPicante(): Boolean {
        return picante
    }

    fun setPicante(picante: Boolean) {
        this.picante = picante
    }

    override fun toString(): String {
        return "Ingredientes{" +
                "vegano=" + vegano +
                ", gluten=" + gluten +
                ", lactosa=" + lactosa +
                ", picante=" + picante +
                '}'
    }
}