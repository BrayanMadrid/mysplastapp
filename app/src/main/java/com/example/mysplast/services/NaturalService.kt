package com.example.mysplast.services

import com.example.mysplast.model.Natural
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface NaturalService {

    @GET("/natural/listar")
    fun getListadoNaturales(@Header("Authorization") accesstoken: String): Call<List<Natural>>

}