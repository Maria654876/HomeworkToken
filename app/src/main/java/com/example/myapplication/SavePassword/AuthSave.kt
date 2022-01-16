package com.example.myapplication.SavePassword

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.R
import com.example.myapplication.preference.AppPreference
import com.google.android.material.textfield.TextInputLayout
import java.io.File

class AuthSave : Fragment() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var saveCredentialCheckBox: AppCompatCheckBox
    private lateinit var buttonLogin : AppCompatButton
    private lateinit var loginField: TextInputLayout
    private lateinit var passwordField: TextInputLayout

    val loginLiveData=MutableLiveData<String>()
    val passwordLiveData=MutableLiveData<String>()




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setShared(AppPreference.getInstance(requireContext()))
        viewModel.fetchCredentials()

        saveCredentialCheckBox=view.findViewById(R.id.save_loginPassword)

        initListeners()
    }

    private fun subscribeOnLiveData(){
        viewModel.saveCredentialsCheck.observe(viewLifecycleOwner, { isSelected ->
            saveCredentialCheckBox.isChecked = isSelected
        })
        viewModel.emailLiveData.observe(viewLifecycleOwner, { email ->
            loginField.editText?.setText(email)
            loginField.editText?.setSelection(email.length)
        })
        viewModel.passwordLiveData.observe(viewLifecycleOwner, { password ->
            passwordField.editText?.setText(password)
            passwordField.editText?.setSelection(password.length)
        })
    }

    @SuppressLint("ResourceType")
    private fun initListeners() {
        saveCredentialCheckBox.setOnCheckedChangeListener{
                _, selected ->
            viewModel.setSaveSelect(selected)
        }

        loginField.editText?.addTextChangedListener {
            it?.let {
                viewModel.setUpdatedEmail(it.toString())
                if (it.isBlank()) {
                    ContextCompat.getColorStateList(requireContext(), R.drawable.auth_input_layout_stroke_color_default)?.let { colorList ->
                        loginField.setBoxStrokeColorStateList(colorList)
                    }
                } else {
                    ContextCompat.getColorStateList(requireContext(), R.drawable.auth_input_layout_stroke_color)?.let { colorList ->
                        loginField.setBoxStrokeColorStateList(colorList)
                    }
                }
            }
        }
        passwordField.editText?.addTextChangedListener {
            viewModel.setUpdatedPassword(it.toString())
        }
        buttonLogin.setOnClickListener {
            val emailText = loginField.editText?.text.toString()
            val passwordText = passwordField.editText?.text.toString()
            viewModel.onLoginClicked(emailText, passwordText)
            viewModel.saveCredentialsToFile(
                emailText,
                passwordText,
                File(requireActivity().getDir("credentials", Context.MODE_PRIVATE).absolutePath + "/" + "credentials.txt")
            )
        }

    }
}