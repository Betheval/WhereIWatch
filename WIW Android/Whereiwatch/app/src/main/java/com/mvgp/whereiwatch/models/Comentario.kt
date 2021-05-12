package com.mvgp.whereiwatch.models

import com.google.firebase.Timestamp
import java.util.*

class Comentario (val comentario:String, val comentarista:String, val id:Long, val puntuacion:Long, val fecha:Timestamp) {
    constructor() : this("","",0,0, Timestamp.now())
}
