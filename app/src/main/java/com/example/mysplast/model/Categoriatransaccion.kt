package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Categoriatransaccion(

    @SerializedName("id")
    var id: Long,

    @SerializedName("nombre")
    var nombre: String,

    @SerializedName("tipotransaccion")
    var tipotransaccion: Tipotransaccion,

)
