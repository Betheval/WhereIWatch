package com.mvgp.whereiwatch.popular

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.mvgp.whereiwatch.models.Pelicula
import com.mvgp.whereiwatch.models.Sitio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularViewModel(application: Application) : AndroidViewModel(application) {

    private var _data = MutableLiveData<MutableList<Pelicula>>()
    val data: LiveData<MutableList<Pelicula>>
        get() = _data


    private var _sitiosList = MutableLiveData<MutableList<Sitio>>()
    val sitiosList: LiveData<MutableList<Sitio>>
        get() = _sitiosList

    private var _peliculasPopularesList = MutableLiveData<MutableList<Pelicula>>()
    val peliculasPopularesList: LiveData<MutableList<Pelicula>>
        get() = _peliculasPopularesList

    val peliculasList = mutableListOf<Pelicula>()

    val frstore = FirebaseFirestore.getInstance()


    init {
        fetchPeliculas()
        fetchSitios()
    }

    /**
     * Pide los sitios a Firebase
     *
      */
    private fun fetchSitios() {
        val sitiosList = mutableListOf<Sitio>()
        frstore.collection("sitios").get().addOnSuccessListener { result ->
            for (doc in result) {
                val sit = doc.toObject(Sitio::class.java)
                sitiosList.add(sit)
                _sitiosList.value = sitiosList
            }
        }
    }

    /**
     * Pide las películas a firebase
     */
    private fun fetchPeliculas() {
        frstore.collection("peliculas").get().addOnSuccessListener { result ->
            for (doc in result) {
                val sit = doc.toObject(Pelicula::class.java)
                peliculasList.add(sit)
                cargaPeliculasPopulares()
            }
        }
    }

    /**
     * Carga las peliculas en la lista
     */
    private fun cargaPeliculasPopulares() {
       _peliculasPopularesList.value = peliculasList
        _data.value = peliculasList
    }

    /**
     * Busca las peliculas y las carga en la lista
     *
     */
    fun buscaPelicula(query: String): Boolean {
        _peliculasPopularesList.value = data.value
        val search = _data.value?.filter { it ->
            it.nombre.contains(query)
        }
        return if (search != null) {
            _peliculasPopularesList.value = search.toMutableList()
            true
        } else {
            false
        }

    }

}