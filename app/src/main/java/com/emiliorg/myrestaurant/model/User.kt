package com.emiliorg.myrestaurant.model

class User {
    private var user_id = 0
    private var name: String? = null
    private var mail: String? = null
    private var pwd: String? = null
    private var register_date: String? = null

    constructor() {}
    constructor(user_id: Int, name: String?, mail: String?, pwd: String?, register_date: String?) {
        this.user_id = user_id
        this.name = name
        this.mail = mail
        this.pwd = pwd
        this.register_date = register_date
    }

    fun getUser_id(): Int {
        return user_id
    }

    fun setUser_id(user_id: Int) {
        this.user_id = user_id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getMail(): String? {
        return mail
    }

    fun setMail(mail: String?) {
        this.mail = mail
    }

    fun getPwd(): String? {
        return pwd
    }

    fun setPwd(pwd: String?) {
        this.pwd = pwd
    }

    fun getRegister_date(): String? {
        return register_date
    }

    fun setRegister_date(register_date: String?) {
        this.register_date = register_date
    }
}