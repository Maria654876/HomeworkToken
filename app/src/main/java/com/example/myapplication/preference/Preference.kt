package com.example.myapplication.preference

interface Preference {
    fun isSaveSelect () : Boolean
    fun setSaveSelect(isSelected: Boolean)

    fun saveLogin(login: String)
    fun savePassword(password: String)

    fun getLogin():String
    fun getPassword():String

    fun saveToken(token: String)
    fun getToken(): String

}