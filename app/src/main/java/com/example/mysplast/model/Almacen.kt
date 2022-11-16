package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Almacen(

    @SerializedName("id_ALMACEN")
    var id_almacen: String,

    @SerializedName("nom_ALMACEN")
    var nom_almacen: String,

    @SerializedName("estado")
    var estado: String,
)
