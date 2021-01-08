package com.emiliorg.myrestaurant.model;

import java.util.ArrayList;

public class Pedido {
    private int id_pedido;
    private String date;

    private ArrayList<Plato> items;
    private String user_id;
    private float total;

    //0 Recibido, 1 Preparacion, 2 Enviado, 3 Entregado
    private int status;

    public Pedido(){
        this.items = new ArrayList<>();
    }

    public Pedido(int id_pedido, String date, int status, String user_id) {
        this.id_pedido = id_pedido;
        this.date = date;
        this.status = status;
        this.items = new ArrayList<>();
        this.user_id = user_id;
        total = 0.00f;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Plato> getItems() {
        return items;
    }

    public void setItems(ArrayList<Plato> items) {
        this.items = items;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void addPlato(Plato plato){
        this.items.add(plato);

    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id_pedido=" + id_pedido +
                ", date='" + date + '\'' +
                ", status=" + status +
                ", items=" + items +
                ", user_id=" + user_id +
                '}';
    }
}
