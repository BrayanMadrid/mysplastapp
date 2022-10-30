package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Rol(

    @SerializedName("id")
    var id: Int,
    @SerializedName("nombre")
    var nombre: String,

)
