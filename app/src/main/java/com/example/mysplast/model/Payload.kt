package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Payload(

    @SerializedName("info_adicional")
    var info_adicional: String,

    @SerializedName("user_name")
    var user_name: String,

    @SerializedName("scope")
    var scope: ArrayList<String>,

    @SerializedName("correo")
    var correo: String,

    @SerializedName("exp")
    var exp: Long,

    @SerializedName("nombre")
    var nombre: String,

    @SerializedName("authorities")
    var authorities: ArrayList<String>,

    @SerializedName("jti")
    var jti: String,

    @SerializedName("client_id")
    var client_id: String,
)
