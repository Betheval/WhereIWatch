package com.mvgp.whereiwatch

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.mvgp.whereiwatch.databinding.ActivityNoticiasBinding
import com.mvgp.whereiwatch.models.Noticia
import com.mvgp.whereiwatch.models.Pelicula
import com.squareup.picasso.Picasso

class NoticiasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoticiasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoticiasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noticia = intent.getParcelableExtra<Noticia>("Noticia")
        val portadaNoticia = findViewById<ImageView>(R.id.noticia_portada)

        if (noticia != null) {
            Picasso.get().load(noticia.imagen).into(portadaNoticia)
        }


        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = noticia!!.titulo

        val subtitulo = findViewById<TextView>(R.id.subtitulo_noticia)
        subtitulo.text = noticia.subtitulo

        val cuerpoNoticia = findViewById<TextView>(R.id.noticia_cuerpo)
        cuerpoNoticia.text = noticia.descripcion

    }
}