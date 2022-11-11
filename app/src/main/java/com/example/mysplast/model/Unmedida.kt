package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Unmedida(

    @SerializedName("id_UNMEDIDA")
    var id_unmedida: String,

    @SerializedName("nom_UNMEDIDA")
    var nom_unmedida: String,

)
