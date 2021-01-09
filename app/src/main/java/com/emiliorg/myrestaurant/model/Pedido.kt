package com.emiliorg.myrestaurant.model

import java.util.*

class Pedido {
    private var id_pedido = 0
    private var date: String? = null
    private var items: ArrayList<Plato?>
    private var user_id: String? = null
    private var total = 0f

    //0 Recibido, 1 Preparacion, 2 Enviado, 3 Entregado
    private var status = 0

    constructor() {
        items = ArrayList()
    }

    constructor(id_pedido: Int, date: String?, status: Int, user_id: String?) {
        this.id_pedido = id_pedido
        this.date = date
        this.status = status
        items = ArrayList()
        this.user_id = user_id
        total = 0.00f
    }

    fun getId_pedido(): Int {
        return id_pedido
    }

    fun setId_pedido(id_pedido: Int) {
        this.id_pedido = id_pedido
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getStatus(): Int {
        return status
    }

    fun setStatus(status: Int) {
        this.status = status
    }

    fun getItems(): ArrayList<Plato?>? {
        return items
    }

    fun setItems(items: ArrayList<Plato?>?) {
        this.items = items!!
    }

    fun getUser_id(): String? {
        return user_id
    }

    fun setUser_id(user_id: String?) {
        this.user_id = user_id
    }

    fun getTotal(): Float {
        return total
    }

    fun setTotal(total: Float) {
        this.total = total
    }

    fun addPlato(plato: Plato?) {
        items?.add(plato)
    }

    override fun toString(): String {
        return "Pedido{" +
                "id_pedido=" + id_pedido +
                ", date='" + date + '\'' +
                ", status=" + status +
                ", items=" + items +
                ", user_id=" + user_id +
                '}'
    }
}