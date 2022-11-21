package com.example.mysplast.services

import com.example.mysplast.model.Almacen
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface AlmacenService {

    @GET("/almacen/listar")
    fun getListadoAlmacenes(@Header("Authorization") accesstoken: String): Call<ArrayList<Almacen>>
}