package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Juridica (

    @SerializedName("razonsocial")
    var razonsocial: String,

    @SerializedName("representante")
    var representante: String,

    @SerializedName("id_ACTIVIDAD")
    var id_ACTIVIDAD: Actividad,

): Persona()
