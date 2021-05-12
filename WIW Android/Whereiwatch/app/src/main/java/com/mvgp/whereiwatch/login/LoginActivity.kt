package com.mvgp.whereiwatch.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvgp.whereiwatch.MainActivity
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.registro.RegistroActivity

private lateinit var auth: FirebaseAuth
val PREFSKEY = "mispreferencias";

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Whereiwatch)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        supportActionBar?.hide()


        val emailInput = findViewById<TextInputLayout>(
            R.id.textInputEmail
        )
        val passwordInput = findViewById<TextInputLayout>(R.id.textInputPassword)
        val enterButton = findViewById<Button>(R.id.enterButton)
        val registerButton = findViewById<Button>(R.id.registerButton)


        auth = Firebase.auth

        enterButton.setOnClickListener {
            val email = emailInput.editText?.text.toString()
            val password = passwordInput.editText?.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            val user = Firebase.auth.currentUser
                            saveValuePreference(this, true, user.uid)
                            startActivity(intent)
                        } else {
                            Snackbar.make(
                                it,
                                "Credenciales incorrectos, revisalos",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    })
            } else {
                Snackbar.make(it, "Campo vacío introducido", Snackbar.LENGTH_LONG).show()
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onBackPressed() {

    }

    private fun saveValuePreference(context: Context, bool: Boolean, uid: String) {
        val settings: SharedPreferences = context.getSharedPreferences(PREFSKEY, MODE_PRIVATE)
        val editor: SharedPreferences.Editor
        editor = settings.edit()
        editor.putBoolean("isPassOnboard", bool)
        editor.putString("uid", uid)
        editor
        editor.apply()
    }


}