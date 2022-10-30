package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Usuario(

    @SerializedName("id")
    var id: Int,
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("nombre")
    var nombre: String,
    @SerializedName("correo")
    var correo: String,
    @SerializedName("enabled")
    var enabled: Boolean,

)
