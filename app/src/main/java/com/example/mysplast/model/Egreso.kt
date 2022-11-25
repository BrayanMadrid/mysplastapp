package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Egreso(

    @SerializedName("id_CENTROCOSTO")
    var id_centrocosto: Centrocosto,

) : Transaccion()
