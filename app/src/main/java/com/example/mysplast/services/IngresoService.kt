package com.example.mysplast.services

import com.example.mysplast.model.Ingreso
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface IngresoService {

    @GET("/ingreso/listartop")
    fun getListadoIngreso(@Header("Authorization") accesstoken: String): Call<ArrayList<Ingreso>>

    @GET("/ingreso/buscar/{id}")
    fun getIngresoxID(@Header("Authorization") accesstoken: String, @Path("id") id: String): Call<Ingreso>

    @GET("/ingreso/filtro")
    fun getFiltroIngreso(@Header("Authorization") accesstoken: String, @Query(value="subalmacen") subalmacen: String, @Query(value="almacen") almacen: String,
                         @Query(value = "fecha1") fecha1: String, @Query(value="fecha2") fecha2: String): Call<ArrayList<Ingreso>>
}