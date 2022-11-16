package com.example.mysplast.services

import com.example.mysplast.model.Producto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ProductosService {

    @GET("/producto/listar")
    fun getListadoProductos(@Header("Authorization") accesstoken: String): Call<List<Producto>>

    @GET("/producto/filtrar-producto/{term}")
    fun getProductosxTermino(@Header("Authorization") accesstoken: String, @Path("term") term: String): Call<List<Producto>>


}