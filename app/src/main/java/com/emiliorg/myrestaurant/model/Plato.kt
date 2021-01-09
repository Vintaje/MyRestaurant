package com.emiliorg.myrestaurant.model

class Plato {
    private var id_plato = 0
    private var name: String? = null
    private var ingredientes: Ingredientes? = null
    private var precio = 0f
    private var image: String? = null

    constructor() {}
    constructor(id_plato: Int, name: String?, ingredients: Ingredientes?, price: Float) {
        this.id_plato = id_plato
        this.name = name
        ingredientes = ingredients
        precio = price
    }

    constructor(id_plato: Int, name: String?, ingredients: Ingredientes?, price: Float, urlPhoto: String?) {
        this.id_plato = id_plato
        this.name = name
        ingredientes = ingredients
        precio = price
        image = urlPhoto
    }

    fun getId_plato(): Int {
        return id_plato
    }

    fun setId_plato(id_plato: Int) {
        this.id_plato = id_plato
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getIngredientes(): Ingredientes? {
        return ingredientes
    }

    fun setIngredientes(ingredientes: Ingredientes?) {
        this.ingredientes = ingredientes
    }

    fun getPrecio(): Float {
        return precio
    }

    fun setPrecio(precio: Float) {
        this.precio = precio
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    override fun toString(): String {
        return "Plato{" +
                "id_plato=" + id_plato +
                ", name='" + name + '\'' +
                ", ingredientes=" + ingredientes.toString() +
                ", precio=" + precio +
                ", image='" + image + '\'' +
                '}'
    }
}