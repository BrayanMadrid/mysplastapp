package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Transferencia(

    @SerializedName("id_SECTORDEST")
    var id_sectordest: Sector,

) : Transaccion()
