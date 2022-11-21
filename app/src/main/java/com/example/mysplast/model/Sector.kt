package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Sector(

    @SerializedName("id_SECTOR")
    var id_sector: String,

    @SerializedName("nom_SECTOR")
    var nom_sector: String,

    @SerializedName("id_ALMACEN")
    var id_almacen: Almacen,
) {
    override fun toString(): String {
        return nom_sector
    }
}
