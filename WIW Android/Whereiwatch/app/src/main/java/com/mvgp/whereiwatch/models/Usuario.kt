package com.mvgp.whereiwatch.models

class Usuario (val correo:String, val nombre:String ,val pendiente:List<Long>, val visto: List<Long>, val esAdmin:Boolean) {
    constructor() : this("","", listOf(0), listOf(0), false)
}