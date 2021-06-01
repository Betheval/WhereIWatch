package com.mvgp.whereiwatch.novedades

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.mvgp.whereiwatch.models.Noticia
import com.mvgp.whereiwatch.models.Sitio

class NewsViewModel : ViewModel() {
    private var _sitiosList = MutableLiveData<MutableList<Sitio>>()
    val sitiosList: LiveData<MutableList<Sitio>>
        get() = _sitiosList

    private var _noticiasList = MutableLiveData<MutableList<Noticia>>()
    val noticiasList: LiveData<MutableList<Noticia>>
        get() = _noticiasList

    val frstore = FirebaseFirestore.getInstance()

    init {
        fetchSitios()
        fetchNoticas()
    }


    /**
     *
     * Pide las noticias a firebase y las guarda en una lista
     */
    private fun fetchNoticas() {
        val nuevasList = mutableListOf<Noticia>()
        frstore.collection("noticias").get().addOnSuccessListener { result ->
            for (doc in result){
                val not = doc.toObject(Noticia::class.java)
                nuevasList.add(not)
                _noticiasList.value = nuevasList

            }
        }

    }

    /**
     * Pide los sitios a Firebase y los guarda en una lista
     */
    private fun fetchSitios(){
        val sitiosList = mutableListOf<Sitio>()
        frstore.collection("sitios").get().addOnSuccessListener { result ->
            for (doc in result){
                val sit = doc.toObject(Sitio::class.java)
                sitiosList.add(sit)
                _sitiosList.value = sitiosList

            }
        }


    }
}