package com.mvgp.whereiwatch.pelicula

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.models.Pelicula
import com.mvgp.whereiwatch.recyclers.HorizontalRecyclerCommentAdapter
import com.mvgp.whereiwatch.recyclers.VerticalRecyclerSitios
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_first.*

class PeliculaActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pelicula)
        val peliculaViewModel = ViewModelProvider(this).get(PeliculaViewModel::class.java)

        val recyclerSites = findViewById<RecyclerView>(R.id.recycler_sites_pelicula)
        val pelicula = intent.getParcelableExtra<Pelicula>("Pelicula")
        val recyclerComments = findViewById<RecyclerView>(R.id.recycler_temporadas)
        val peliculaRatingBar = findViewById<RatingBar>(R.id.peliculaRatingBar)
        val textComment = findViewById<EditText>(R.id.editTextMultiPelicula)
        val sendCommentButton = findViewById<Button>(R.id.send_cooment_button)
        val addVistoButton = findViewById<Button>(R.id.button_add_visto)

        val addPendingButton = findViewById<Button>(R.id.button_add_pending)


        //declaracion del adaptador
        val adapterSites = VerticalRecyclerSitios()
        recyclerSites.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerSites.adapter = adapterSites

        //Recycler comments
        recyclerComments.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapterComments = HorizontalRecyclerCommentAdapter()
        recyclerComments.adapter = adapterComments

        val progressBarSites = findViewById<ProgressBar>(R.id.progressBarSites)
        peliculaViewModel.listaSitios.observe(this, Observer {
            Log.i("Enviado a recyclerSitios", it[0].nombre)
            progressBarSites.visibility = View.GONE
            adapterSites.submitList(it)

        })


        if (pelicula != null) {
            peliculaViewModel.fetchSitios(pelicula)
            peliculaViewModel.fetchListas()
            peliculaViewModel.fetchComentarios(pelicula)

        }

        setSupportActionBar(findViewById(R.id.toolbar))
        if (pelicula != null) {
            findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = pelicula.nombre

            val portada = findViewById<ImageView>(R.id.pelicula_portada_imgbar)
            Picasso.get().load(pelicula.portada).resize(100, 100).into(portada)

            findViewById<TextView>(R.id.sinopsis).text = pelicula.sinopsis



            addVistoButton.setOnClickListener {
                // Lo añade

                if (!peliculaViewModel.isInVisto(pelicula)) {
                    peliculaViewModel.updateLists(pelicula, "V", "A")
                    it.setBackgroundColor(resources.getColor(R.color.secondaryColor))
                } else {
                    // lo quita
                    if (peliculaViewModel.isInVisto(pelicula)) {
                        peliculaViewModel.updateLists(pelicula, "V", "D")
                        it.setBackgroundColor(resources.getColor(R.color.primaryColor))
                    }

                }
            }
            addPendingButton.setOnClickListener {
                //Lo añade
                if (!peliculaViewModel.isInPendiente(pelicula)) {
                    peliculaViewModel.updateLists(pelicula, "P", "A")
                    it.setBackgroundColor(resources.getColor(R.color.secondaryColor))

                } else {
                    //Lo quita
                    if (peliculaViewModel.isInPendiente(pelicula)) {
                        peliculaViewModel.updateLists(pelicula, "P", "D")
                        it.setBackgroundColor(resources.getColor(R.color.primaryColor))
                    }
                }
            }
            //Enviar comentario

            sendCommentButton.setOnClickListener {
                val rating = peliculaRatingBar.rating
                val com: String = textComment.text.toString()
                peliculaViewModel.addComment(pelicula, rating, "UID", com, adapterComments)
                Snackbar.make(it,"Se ha añadido tu comentario", Snackbar.LENGTH_LONG)

            }


        }




        peliculaViewModel.listaComentario.observe(this, Observer {
            Log.i("Enviado a recyclerComents", it[0].toString())
//            progressBarSites.visibility = View.GONE
            adapterComments.submitList(it)

        })
        peliculaViewModel.listas.observe(this, Observer {
            if (peliculaViewModel.isInVisto(pelicula!!)) {
                addVistoButton.setBackgroundColor(resources.getColor(R.color.secondaryColor))
            } else if (peliculaViewModel.isInPendiente(pelicula)) {
                addPendingButton.setBackgroundColor(resources.getColor(R.color.secondaryColor))
            }
        })
    }


}


