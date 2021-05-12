package com.mvgp.whereiwatch.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Pelicula (var id:Long, val nombre:String, val ratingPoints:Long, val anio:String, val portada:String, val director:String, val sinopsis:String, val sitios:List<Long>):Parcelable{
    constructor() : this(0,"",0,"","","","", listOf())

}

