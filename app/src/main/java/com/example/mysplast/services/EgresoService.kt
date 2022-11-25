package com.example.mysplast.services

import com.example.mysplast.model.Egreso
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface EgresoService {

    @GET("/egreso/listartop")
    fun getListadoEgreso(@Header("Authorization") accesstoken: String): Call<ArrayList<Egreso>>

    @GET("/egreso/buscar/{id}")
    fun getEgresoxID(@Header("Authorization") accesstoken: String, @Path("id") id: String): Call<Egreso>

    @GET("/egreso/filtro")
    fun getFiltroEgreso(@Header("Authorization") accesstoken: String, @Query(value="subalmacen") subalmacen: String, @Query(value="almacen") almacen: String,
                        @Query(value = "fecha1") fecha1: String, @Query(value="fecha2") fecha2: String): Call<ArrayList<Egreso>>
}