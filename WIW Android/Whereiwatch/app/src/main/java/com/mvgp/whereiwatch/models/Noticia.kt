package com.mvgp.whereiwatch.models



import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Noticia(val id:Long, val titulo:String, val subtitulo:String, val descripcion:String, val imagen:String):Parcelable {
    constructor() : this(0, "","","", "")
}