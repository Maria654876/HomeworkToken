package com.example.myapplication.SavePassword

import android.content.SharedPreferences
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.preference.AppPreference
import com.example.myapplication.preference.AuthService
import com.example.myapplication.preference.AuthServiceI
import com.example.myapplication.preference.Preference
import java.io.*

class AuthViewModel: ViewModel(), LifecycleObserver {

    companion object {
        private const val TAG = "AuthViewModel"
    }

    private var preferences: Preference?=null


    private val authModel: AuthService = AuthServiceI()

    val emailLiveData = MutableLiveData<String>()
    val passwordLiveData = MutableLiveData<String>()
    val saveCredentialsCheck = MutableLiveData<Boolean>()

    fun setShared(preferences: Preference){
        this.preferences = preferences
    }

    fun setSaveSelect (isSelected: Boolean){
        preferences?.setSaveSelect(isSelected)
    }

    fun onLoginClicked(email: String, password: String){

        val token = authModel.onLoginClicked(email, password)

        if (token!=null){
            saveToken(token)
            loginSuccess(email,password)
        } else{

        }
    }

    private fun loginSuccess(email: String, password: String){
        preferences?.let{
            if(it.isSaveSelect()){
                it.saveLogin(email)
                it.savePassword(password)
            }
        }
    }

    private fun saveToken(token: String){
        preferences?.saveToken(token)
    }

    fun fetchCredentials() {
        preferences?.let{
            if(it.isSaveSelect()){
              emailLiveData.postValue(it.getLogin())
              passwordLiveData.postValue(it.getPassword())
              saveCredentialsCheck.postValue(true)

            }
        }
    }
    private fun getCredentialsFromFile(file: File) {
        var fis: FileInputStream? = null

        try {
            fis = FileInputStream(file)
            val isr = InputStreamReader(fis)
            val bsr = BufferedReader(isr)
            val stringBuilder = StringBuilder()
            val iterator = bsr.lineSequence().iterator()

            while (iterator.hasNext()) {
                stringBuilder.append(iterator.next())
            }

            val result = stringBuilder.toString()

            if (result.isNotBlank()) {
                val credentials = result.split(",")

                val email = credentials[0]
                val password = credentials[1]

                println("CREDENTIALS: email = $email, password = $password")
            }
        } catch (error: Exception) {
            Log.e(TAG, error.message ?: "")
        } finally {
            fis?.close()
        }
    }

    fun saveCredentialsToFile(email: String, password: String, file: File) {
        var fos: FileOutputStream? = null

        try {
            file.createNewFile()

            fos = FileOutputStream(file)
            fos.write("$email, $password".toByteArray())
        } catch (error: Exception) {
            Log.e(TAG, error.message ?: "")
        } finally {
            fos?.close()
            getCredentialsFromFile(file)
        }
    }

    fun setUpdatedEmail(email: String) {
        if (email != emailLiveData.value) {
            emailLiveData.value = email
        }
    }

    fun setUpdatedPassword(password: String) {
        if (password != passwordLiveData.value) {
            passwordLiveData.value = password
        }
    }


}