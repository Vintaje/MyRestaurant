package com.emiliorg.myrestaurant.model;

public class User {
    private int user_id;
    private String name;
    private String mail;
    private String pwd;
    private String register_date;

    public User(){

    }

    public User(int user_id, String name, String mail, String pwd, String register_date) {
        this.user_id = user_id;
        this.name = name;
        this.mail = mail;
        this.pwd = pwd;
        this.register_date = register_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }
}
