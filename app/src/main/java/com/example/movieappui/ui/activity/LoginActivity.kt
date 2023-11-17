package com.example.movieappui.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.example.movieappui.R
import com.example.movieappui.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null &&text.length >= 8
    }

    private fun initView() {
        val edtUser = binding.edtUser
        val edtPass = binding.edtPassword
        binding.btnLogin.setOnClickListener {
            if(!isPasswordValid(edtPass.text!!)){
                edtPass.error = getString(R.string.error_pass)
            }
            if(edtUser.text.toString().isEmpty()){
                edtUser.error = getString(R.string.error_user)
            }
            else if(isPasswordValid(edtPass.text!!)){
                edtPass.error = null
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        edtPass.setOnKeyListener { _, _, _ ->
            if(isPasswordValid(edtPass.text!!)){
                edtPass.error = null
            }
            false
        }
    }
}