package com.example.mysplast.services

import com.example.mysplast.model.Transferencia
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TransferenciaService {

    @GET("/transferencia/listartop")
    fun getListadoTransferencia(@Header("Authorization") accesstoken: String): Call<ArrayList<Transferencia>>

    @GET("/transferencia/buscar/{id}")
    fun getTransferenciaxID(@Header("Authorization") accesstoken: String, @Path("id") id: String): Call<Transferencia>

    @GET("/transferencia/filtro")
    fun getFiltroTransferencia(@Header("Authorization") accesstoken: String, @Query(value="subalmacen") subalmacen: String, @Query(value="almacen") almacen: String,
                               @Query(value = "fecha1") fecha1: String, @Query(value="fecha2") fecha2: String): Call<ArrayList<Transferencia>>
}