package com.example.mysplast.services

import com.example.mysplast.model.Ordencompra
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface OrdenCompraService {

    @GET("/ordencompra/listartop")
    fun getListadoOrdenesCompra(@Header("Authorization") accesstoken: String): Call<ArrayList<Ordencompra>>

    @GET("/ordencompra/buscar/{id}")
    fun getOrdenCompraxID(@Header("Authorization") accesstoken: String, @Path("id") id: String): Call<Ordencompra>

    @GET("/ordencompra/filtro")
    fun getFiltroOrdenesCompra(@Header("Authorization") accesstoken: String, @Query(value="subalmacen") subalmacen: String, @Query(value="almacen") almacen: String,
                             @Query(value = "fecha1") fecha1: String, @Query(value="fecha2") fecha2: String): Call<ArrayList<Ordencompra>>
}