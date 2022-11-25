package com.example.mysplast.model

import com.google.gson.annotations.SerializedName
import java.util.Date

abstract class Transaccion(

) {
    @SerializedName("id_TRAN")
    lateinit var id_tran: String

    @SerializedName("nro_TRAN")
    lateinit var nro_TRAN: String

    @SerializedName("fechatran")
    lateinit var fechatran: Date

    @SerializedName("desc_TRAN")
    lateinit var desc_TRAN: String

    @SerializedName("estado")
    lateinit var estado: String

    @SerializedName("id_SECTOR")
    lateinit var id_sector: Sector

    @SerializedName("id_TIPOTRANSACCION")
    lateinit var id_tipotransaccion: Tipotransaccion

    @SerializedName("categoriatransaccion")
    lateinit var categoriatransaccion: Categoriatransaccion

    @SerializedName("id_PERSONA")
    lateinit var id_PERSONA: Natural

    @SerializedName("items")
    lateinit var items: List<Itemtransaccion>
}
