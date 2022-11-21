package com.example.mysplast.services

import com.example.mysplast.model.Token
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("oauth/token")
    fun autenticarse(
        @Header("Authorization") authorization: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grant_type: String
    ): Call<Token>

    companion object Factory{

        private const val BASE_URL = "http://192.168.3.36:8080/"
        fun create():ApiService{
            var retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()).build()
            return retrofit.create(ApiService::class.java)
        }
    }

}