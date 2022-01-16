package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myapplication.SavePassword.AuthSave
import com.example.myapplication.preference.AppPreference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            if (AppPreference.getInstance(this).getToken().isBlank()) {
                openFragment(AuthSave(), tag = "LocationFragment")
            } else {
            }
        }
    }

    fun openFragment(fragment: Fragment, doClearBackStack: Boolean = false, tag: String? = null) {
        if (doClearBackStack) {
            clearBackStack()
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.text, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
    private fun clearBackStack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}