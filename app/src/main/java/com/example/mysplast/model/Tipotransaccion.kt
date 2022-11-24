package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Tipotransaccion(

    @SerializedName("id")
    var id: Int,

    @SerializedName("nom_TIPOTRANSACCION")
    var nom_tipotransaccion: String,

    @SerializedName("tipo")
    var tipo: String,

)
