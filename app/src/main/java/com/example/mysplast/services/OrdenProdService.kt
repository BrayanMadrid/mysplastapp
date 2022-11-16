package com.example.mysplast.services

import com.example.mysplast.model.Ordenprod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface OrdenProdService {

    @GET("/ordenprod/listartop")
    fun getListadoOrdenesProduccion(@Header("Authorization") accesstoken: String): Call<List<Ordenprod>>
}