package com.example.mysplast.services

import com.example.mysplast.model.Producto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ProductosService {

    @GET("/producto/listar")
    fun getListadoProductos(@Header("Authorization") accesstoken: String): Call<Producto>


}