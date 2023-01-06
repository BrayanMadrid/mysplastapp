package com.example.mysplast.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class InventarioFisico(

    @SerializedName("id_INVENT")
    var id_invent: String,

    @SerializedName("fecha")
    var fecha: Date,

    @SerializedName("estado")
    var estado: String,

    @SerializedName("nroinventario")
    var nroinventario: String,

    @SerializedName("responsable")
    var responsable: Natural,

    @SerializedName("id_SECTOR")
    var id_sector: Sector,

    @SerializedName("items")
    var items: List<ItemInventarioFisico>,

)
