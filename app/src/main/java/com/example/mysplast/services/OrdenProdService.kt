package com.example.mysplast.services

import com.example.mysplast.model.Ordenprod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface OrdenProdService {

    @GET("/ordenprod/listartop")
    fun getListadoOrdenesProduccion(@Header("Authorization") accesstoken: String): Call<List<Ordenprod>>

    @GET("/ordenprod/buscar/{id}")
    fun getOrdenxID(@Header("Authorization") accesstoken: String, @Path("id") id: String): Call<Ordenprod>

    @GET("/ordenprod/filtro")
    fun getFiltroOrdenesProd(@Header("Authorization") accesstoken: String, @Query(value="subalmacen") subalmacen: String, @Query(value="almacen") almacen: String,
    @Query(value = "fecha1") fecha1: String, @Query(value="fecha2") fecha2: String, @Query(value="estado") estado: String): Call<ArrayList<Ordenprod>>
}