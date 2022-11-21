package com.example.mysplast.services

import com.example.mysplast.model.Sector
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SectorService {

    @GET("/sector/buscar/almacen/{alm}")
    fun getObtenerSectorxAlmacen(@Header("Authorization") accesstoken: String, @Path("alm") alm: String): Call<ArrayList<Sector>>
}