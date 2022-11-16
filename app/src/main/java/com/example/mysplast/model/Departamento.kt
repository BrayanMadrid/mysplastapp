package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Departamento(

    @SerializedName("id_departamento")
    var id_departamento: String,

    @SerializedName("ine_departamento")
    var ine_departamento: String,

    @SerializedName("nom_departamento")
    var nom_departamento: String,
)
