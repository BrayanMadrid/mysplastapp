package com.example.mysplast.services

import com.example.mysplast.model.Recetaprod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RecetaProdService {

    @GET("/recetaprod/listar")
    fun getListadoRecetasProduccion(@Header("Authorization") accesstoken: String): Call<List<Recetaprod>>

    @GET("/recetaprod/buscar/{id}")
    fun getRecetaxID(@Header("Authorization") accesstoken: String, @Path("id") id: String): Call<Recetaprod>

    @GET("/recetaprod/filtro")
    fun getFiltroRecetaProd(@Header("Authorization") accesstoken: String, @Query(value="nroreceta") nroreceta: String, @Query(value="producto") producto: String,
                             @Query(value = "nomreceta") nomreceta: String): Call<List<Recetaprod>>

}