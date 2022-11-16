package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Tipodoc(

    @SerializedName("id_TIPODOC")
    var id_tipodoc: String,

    @SerializedName("nom_TIPODOC")
    var nom_tipodoc: String,

    @SerializedName("cant_TIPODOC")
    var cant_tipodoc: String,
)
