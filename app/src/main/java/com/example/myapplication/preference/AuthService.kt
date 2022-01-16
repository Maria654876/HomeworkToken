package com.example.myapplication.preference

interface AuthService {
    fun onLoginClicked(email: String, password: String): String?
    fun updateUserData(data: Any)
}