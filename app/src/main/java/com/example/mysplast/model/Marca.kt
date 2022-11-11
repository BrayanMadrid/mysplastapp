package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Marca(

    @SerializedName("id_MARCA")
    var id_marca: String,

    @SerializedName("nom_MARCA")
    var nom_marca: String,

)
