package com.example.mysplast.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Ordenprod(

    @SerializedName("id_ORDENPROD")
    var id_ordenprod: String,

    @SerializedName("nro_ORDENPROD")
    var nro_ordenprod: String,

    @SerializedName("desc_ORDENPROD")
    var desc_ordenprod: String,

    @SerializedName("fecha")
    var fecha: Date,

    @SerializedName("estado")
    var estado: String,

    @SerializedName("id_SECTOR")
    var id_sector: Sector,

    @SerializedName("id_SECTORINSUMOS")
    var id_sectorinsumos: Sector,

    @SerializedName("id_PERSONA")
    var id_PERSONA: Natural,

    @SerializedName("items")
    var items: ArrayList<ItemOrdenprod>
)
