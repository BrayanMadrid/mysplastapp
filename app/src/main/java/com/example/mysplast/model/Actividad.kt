package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Actividad(

    @SerializedName("id_ACTIVIDAD")
    var id_actividad: Long,

    @SerializedName("nom_ACTIVIDAD")
    var nom_ACTIVIDAD: String,
)
