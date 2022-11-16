package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Distrito(

    @SerializedName("id_distrito")
    var id_distrito: String,

    @SerializedName("ine_distrito")
    var ine_distrito: String,

    @SerializedName("nom_distrito")
    var nom_distrito: String,

    @SerializedName("id_provincia")
    var id_provincia: Provincia,

)
