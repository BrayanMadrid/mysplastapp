package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Provincia(

    @SerializedName("id_provincia")
    var id_provincia: String,

    @SerializedName("ine_dprovincia")
    var ine_dprovincia: String,

    @SerializedName("nom_provincia")
    var nom_provincia: String,

    @SerializedName("id_departamento")
    var id_departamento: Departamento


)
