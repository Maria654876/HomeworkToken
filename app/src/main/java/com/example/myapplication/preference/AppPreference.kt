package com.example.myapplication.preference

import android.content.Context
import android.content.SharedPreferences

class AppPreference private constructor(): Preference {

    private var preference: SharedPreferences?=null


    companion object{
        private const val PREFERENCE_NAME="AppPreferences"
        private const val PREFERENCE_IS_SAVE="PREFERENCE_IS_SAVE"
        private const val PREFERENCE_LOGIN="PREFERENCE_LOGIN"
        private const val PREFERENCE_PASSWORD="PREFERENCE_PASSWORD"
        private const val PREFERENCE_TOKEN="PREFERENCE_TOKEN"
        private var instance: Preference?=null
        private var preferences:SharedPreferences?=null

        fun getInstance(context: Context): Preference{
            if (instance == null){
                instance=AppPreference()
                preferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            }
            return  instance!!
        }
    }



    override fun isSaveSelect(): Boolean {
       return preferences?.getBoolean(PREFERENCE_IS_SAVE,false)?: false
    }

    override fun setSaveSelect(isSelected: Boolean) {
        preferences?.let {it.edit().putBoolean(PREFERENCE_IS_SAVE, isSelected)?.apply()}
    }

    override fun saveLogin(login: String): Unit =
        preferences?.let {it.edit().putString(PREFERENCE_LOGIN, login)?.apply()}!!


    override fun savePassword(password: String) :Unit=
        preferences?.let {it.edit().putString(PREFERENCE_PASSWORD, password)?.apply()}!!


    override fun getLogin(): String  =preferences?.getString(PREFERENCE_LOGIN,"")?: ""


    override fun getPassword(): String =preferences?.getString(PREFERENCE_PASSWORD,"")?:""

    override fun saveToken(token: String) =preferences?.let {it.edit().putString(PREFERENCE_TOKEN, token)?.apply()}!!



    override fun getToken(): String =preferences?.getString(PREFERENCE_TOKEN,"")?: ""
}