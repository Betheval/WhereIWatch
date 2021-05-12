package com.mvgp.whereiwatch.list

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

import com.mvgp.whereiwatch.login.PREFSKEY
import com.mvgp.whereiwatch.models.Pelicula
import com.mvgp.whereiwatch.models.Sitio
import com.mvgp.whereiwatch.models.Usuario
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private var _peliculasList = MutableLiveData<MutableList<Pelicula>>()
    val peliculasList: LiveData<MutableList<Pelicula>>
        get() = _peliculasList

    var listaVisto: MutableList<Long> = mutableListOf()
    private var listaPendiente: MutableList<Long> = mutableListOf()


    private var _peliculasVistas = MutableLiveData<MutableList<Pelicula>>()
    val peliculasVistas: LiveData<MutableList<Pelicula>>
        get() = _peliculasVistas


    private var _peliculasPendientes = MutableLiveData<MutableList<Pelicula>>()
    val peliculasPendientes: LiveData<MutableList<Pelicula>>
        get() = _peliculasPendientes


    private var _data = MutableLiveData<MutableList<Pelicula>>()
    val data: LiveData<MutableList<Pelicula>>
        get() = _data

    var uid:String =""


    val PREFSKEY = "mispreferencias";
    val allPeliculasList:MutableList<Pelicula> = mutableListOf()
    val frstore = FirebaseFirestore.getInstance()


        init {

        fetchPeliculas(application)

    }


    /**
     * Obtiene las listas
     */
    fun getLists() {
        var usuario:Usuario = Usuario()
        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios").document(uid).get()
            .addOnSuccessListener {
                usuario = it.toObject(Usuario::class.java)!!
                listaVisto = usuario.visto.toMutableList()
                getPeliculasVistas()
                listaPendiente = usuario.pendiente.toMutableList()
                getPeliculasPendientes()
                onListPelisCharger(true)

            }



    }

    /**
     *
     * Obtiene la uid del usuario
     */
    private fun getUid(application: Application): String? {
        val preferences: SharedPreferences = application.getSharedPreferences(
            PREFSKEY,
            AppCompatActivity.MODE_PRIVATE
        )
        return preferences.getString("uid", "")
    }

    /**
     *
     * Buscador de peliculas
     */
    fun buscaPelicula(query: String): Boolean {
         _peliculasList.value =  _data.value

        val search = _data.value?.filter { it ->
            it.nombre.contains(query)
        }
        return if (search != null) {
            _peliculasList.value = search.toMutableList()
            true
        } else {
            false
        }

    }

    /**
     * Carga lista de peliculas vistas
     */
    fun getPeliculasVistas() {
        var lista = listaVisto
        val list: MutableList<Pelicula> = mutableListOf()
        for (id in listaVisto){
            val pel = allPeliculasList.filter { pelicula -> pelicula.id == id  }
            list.add(pel[pel.size - 1 ])
        }
        _peliculasVistas.value = list


    }

    /**
     * Carga la lista de peliculas pendientes
     */
    fun getPeliculasPendientes() {
        var lista = listaPendiente
        val list: MutableList<Pelicula> = mutableListOf()
        for (id in listaPendiente){
            val pel = allPeliculasList.filter { pelicula -> pelicula.id == id  }
            list.add(pel[pel.size - 1 ])
        }
        _peliculasPendientes.value = list


    }

    /**
     * Se combina con el radiobutton de la lista para ofrecer una lista u otra
     */
    fun onListPelisCharger(vistas:Boolean){
        if (vistas){
            _peliculasList.value = _peliculasVistas.value
            _data.value = _peliculasVistas.value
        }else{
            _peliculasList.value = _peliculasPendientes.value
            _data.value = _peliculasPendientes.value
        }
    }


    /**
     * Pide todas las peliculas y las guarda en una lista
     */
     fun fetchPeliculas(application: Application) {
        uid = getUid(application).toString()
        frstore.collection("peliculas").get().addOnSuccessListener { result ->
            for (doc in result) {
                val pel = doc.toObject(Pelicula::class.java)
                allPeliculasList.add(pel)
            }
            getLists()

        }
    }

}

