package com.mvgp.whereiwatch.inicio

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mvgp.whereiwatch.models.Pelicula
import com.mvgp.whereiwatch.models.Recomendados

class InicioViewModel(application:Application) : AndroidViewModel(application) {
    private var peliculas:MutableList<Pelicula> = mutableListOf()


    private var _peliculasRecommend = MutableLiveData<MutableList<Pelicula>>()
    val peliculasRecommend:LiveData<MutableList<Pelicula>>
        get() = _peliculasRecommend

    private var _peliculasRecommendedExtra = MutableLiveData<MutableList<Pelicula>>()
    val peliculasRecommendedExtra:LiveData<MutableList<Pelicula>>
        get() = _peliculasRecommendedExtra

    var listaVisto:MutableList<Long> = mutableListOf()
    var listaPendiente:MutableList<Long> = mutableListOf()

    val frstore = FirebaseFirestore.getInstance()




    init {
        getListRecommendedLists(application)
//        fetchPeliculas(application)
    }




    /**
     * Obtiene los primeros recomendados
     */
    private fun getListRecommendedLists(application: Application) {
        val fireDb = FirebaseFirestore.getInstance()
        fireDb.collection("recomendado").document("u6isxsjOIC6uCzBLlmb6").get().addOnSuccessListener {

            val rec = it["recomendado"] as MutableList<Long>
            val recextra = it["recomendadoextra"] as MutableList<Long>

            rec.forEach{num -> listaVisto.add(num)}
            recextra.forEach{num -> listaPendiente.add(num)}
            fetchPeliculas(application)


        }

    }



    /**
     * Obtiene las peliculas de la base de datos y las trae a la Mutable list.
     */
    private fun fetchPeliculas(application: Application){

        frstore.collection("peliculas").get().addOnSuccessListener { result ->
            val pelist:MutableList<Pelicula> = mutableListOf()
            for (doc in result) {
                val pel = doc.toObject(Pelicula::class.java)
                peliculas.add(pel)
            }

            rellenaPeliculas()
        }
    }


    private fun rellenaPeliculas() {
        val peliculasListExtra:MutableList<Pelicula> = mutableListOf()
        val peliculasListRecom:MutableList<Pelicula> = mutableListOf()
        listaVisto.forEach{id -> peliculas.find { pelicula -> pelicula.id == id }?.let {
            peliculasListRecom.add(
                it
            )
        } }
        listaPendiente.forEach{id -> peliculas.find { pelicula -> pelicula.id == id }?.let {
            peliculasListExtra.add(
                it
            )
        } }

        _peliculasRecommendedExtra.value = peliculasListRecom
        _peliculasRecommend.value = peliculasListExtra





    }





    }
