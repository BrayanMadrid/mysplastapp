package com.example.mysplast.services

import com.example.mysplast.model.InventarioFisico
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface InventarioFisicoService {

    @GET("/inventariofisico/listartop")
    fun getListadoInventarioFisicosProduccion(@Header("Authorization") accesstoken: String): Call<ArrayList<InventarioFisico>>

    @GET("/inventariofisico/buscar/{id}")
    fun getInventarioFisicoxID(@Header("Authorization") accesstoken: String, @Path("id") id: String): Call<InventarioFisico>

    @GET("/inventariofisico/filtro")
    fun getFiltroInventarioFisicoProd(@Header("Authorization") accesstoken: String, @Query(value="sector") sector: String, @Query(value="fecha1") fecha1: String,
                            @Query(value = "fecha2") fecha2: String): Call<ArrayList<InventarioFisico>>

}