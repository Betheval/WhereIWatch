package com.mvgp.whereiwatch.pelicula

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mvgp.whereiwatch.models.Comentario
import com.mvgp.whereiwatch.models.Pelicula
import com.mvgp.whereiwatch.models.Sitio
import com.mvgp.whereiwatch.models.Usuario
import com.mvgp.whereiwatch.recyclers.HorizontalRecyclerCommentAdapter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.TemporalField

class PeliculaViewModel(application: Application) : AndroidViewModel(application) {

    private val PREFSKEY = "mispreferencias";
    private val preferences: SharedPreferences = application.getSharedPreferences(
        PREFSKEY,
        AppCompatActivity.MODE_PRIVATE
    )

    val firestore = FirebaseFirestore.getInstance()
    val sitiosRef = firestore.collection("sitios")

    val commentsRef = firestore.collection("comentarios")

    private var _listaSitios = MutableLiveData<MutableList<Sitio>>()
    val listaSitios:LiveData<MutableList<Sitio>>
        get() = _listaSitios

    private var _listaComentarios = MutableLiveData<MutableList<Comentario>>()
    val listaComentario:LiveData<MutableList<Comentario>>
        get() = _listaComentarios

    private var _listas = MutableLiveData<MutableList<Long>>()
    val listas :LiveData<MutableList<Long>>
        get() = _listas


    var listaComentarios: MutableList<Comentario> = mutableListOf()

    val uid: String = preferences.getString("uid", null)!!
    val userRef = firestore.collection("usuarios").document(uid)
    var listaVisto: MutableList<Long> = mutableListOf()
    var listaPendiente: MutableList<Long> = mutableListOf()
    var usuario:Usuario = Usuario()

    init {


    }

    /**
     * Pide los sitios y los guarda en una lista
     */
    fun fetchSitios(pelicula: Pelicula) {
        val listSites: MutableList<Sitio> = mutableListOf()
        sitiosRef.get().addOnSuccessListener {
            for (doc in it.documents) {
                val sitio = doc.toObject(Sitio::class.java)
                if (sitio != null) {
                    if (pelicula.sitios.contains(sitio.id)) {
                        listSites.add(sitio)
                        _listaSitios.value = listSites
                    }

                }
            }
        }
    }

    /**
     * Pide las listas a firebase
     */
    fun fetchListas() {
        userRef.get().addOnSuccessListener {
            usuario = it.toObject(Usuario::class.java)!!
            usuario.visto?.forEach { idpelicula -> listaVisto.add(idpelicula) }
            usuario.pendiente?.forEach { idpelicula -> listaPendiente.add(idpelicula)
                val finList : MutableList<Long> = (listaVisto + listaPendiente) as MutableList<Long>
            _listas.value = finList}
        }
    }

    /**
     * Pide los comentarios de la pelicula a firebase y los carga en la lista.
     */
    fun fetchComentarios(pelicula: Pelicula) {
        val listComent: MutableList<Comentario> = mutableListOf()
        commentsRef.get().addOnSuccessListener {
            for (doc in it.documents) {
                val comentario = doc.toObject(Comentario::class.java)
                if (comentario != null) {
                    if (comentario.id == pelicula.id) {
                        listComent.add(comentario)
                        listComent.sortByDescending { comentario -> comentario.fecha  }
                        _listaComentarios.value = listComent
                    }
                }

            }
        }
    }

    /**
     * Actualiza las listas de pendientes y vistos
     */
    fun updateLists(pelicula: Pelicula, type: String, action: String) {
        if (type == "V") {
            if (action == "A") {
                listaVisto.add(pelicula.id)
                val finalListVA:List<Long> = listaVisto

                userRef.update(
                    mapOf(
                        "visto" to finalListVA
                    )
                )
            } else {
                val finalListVD = listaVisto.filter { num -> num != pelicula.id }
                userRef.update(
                    mapOf(
                        "visto" to finalListVD
                    )
                )
            }
        } else {
            if (action == "A") {
                listaPendiente.add(pelicula.id)
                val finalListPA = listaPendiente
                userRef.update(
                    mapOf(
                        "pendiente" to finalListPA
                    )
                )

            } else {
                val finalListPD = listaPendiente.filter { num -> num != pelicula.id }
                userRef.update(
                    mapOf(
                        "pendiente" to finalListPD
                    )
                )


            }
        }


    }

    /**
     * Retorna True o False si la pelicula está en la lista de Pendientes
     */
    fun isInPendiente(pelicula: Pelicula): Boolean {
        return listaPendiente.contains(pelicula.id)


    }

    /**
     * Retorna True o False si la película está en la lista de Vistos
     */
    fun isInVisto(pelicula: Pelicula): Boolean {
        return listaVisto.contains(pelicula.id)

    }

    /**
     * Añade comentario a la lista de comentarios de la pelicula
     */
    fun addComment(pelicula: Pelicula, rating:Float,nombre:String, contenido:String, adapter:HorizontalRecyclerCommentAdapter) {

        val coment:Comentario  = Comentario(contenido,usuario.nombre,pelicula.id,rating.toLong(), Timestamp.now() )
        commentsRef.add(coment)
        fetchComentarios(pelicula)
        adapter.notifyDataSetChanged()


    }


}