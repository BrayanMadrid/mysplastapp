package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Categoria(

    @SerializedName("id_CATEGORIA")
    var id_categoria: String,

    @SerializedName("nom_CATEGORIA")
    var nom_categoria: String,

)
