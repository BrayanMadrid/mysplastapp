package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Centrocosto(

    @SerializedName("id_CENTROCOSTO")
    var id_centrocosto: Long,

    @SerializedName("nom_CENTROCOSTO")
    var nom_centrocosto: String,

)
