package com.mvgp.whereiwatch.registro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.models.Usuario
import kotlinx.android.synthetic.main.fragment_first.view.*

var fbAuth = FirebaseAuth.getInstance()
var fbDB = FirebaseFirestore.getInstance()

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val regisNombre = findViewById<TextInputLayout>(R.id.textInputLayoutRegNombre)
        val regisEmail = findViewById<TextInputLayout>(R.id.textInputLayoutRegEmail)
        val regisPassword = findViewById<TextInputLayout>(R.id.textInputLayoutRegPassword)
        val userRef = fbDB.collection("usuarios")
        val regisButton = findViewById<Button>(R.id.button_registro)

        regisButton.setOnClickListener { view ->
            val nombre = regisNombre.editText!!.text.toString()
            val email = regisEmail.editText!!.text.toString()
            val pass = regisPassword.editText!!.text.toString()

            if (nombre.isNotEmpty() and email.isNotEmpty() and pass.isNotEmpty()) {

                fbAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val uid = fbAuth.uid
                        if (uid != null) {
                            val user = Usuario(email, nombre, emptyList(), emptyList())
                            userRef.document(uid).set(user)
                            Snackbar.make(view, "Se ha registrado con exito", Snackbar.LENGTH_LONG)
                                .show()
                        } else {
                            Snackbar.make(
                                view,
                                "No se ha podido procesar la solicitud",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }


                    }
                }
            }else{
                Snackbar.make(view,"Revisa los campos introducidos",Snackbar.LENGTH_LONG).show()
            }


        }


    }
}