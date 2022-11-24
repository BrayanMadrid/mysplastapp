package com.example.mysplast.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Ordencompra(

    @SerializedName("id_ORDENCOMPRA")
    var id_ordencompra: String,

    @SerializedName("fecha")
    var fecha: Date,

    @SerializedName("nroordencompra")
    var nroordencompra: String,

    @SerializedName("moneda")
    var moneda: String,

    @SerializedName("tipopago")
    var tipopago: String,

    @SerializedName("subtotal")
    var subtotal: Double,

    @SerializedName("igv")
    var igv: Double,

    @SerializedName("total")
    var total: Double,

    @SerializedName("estado")
    var estado: String,

    @SerializedName("desc_ORDENCOMPRA")
    var desc_ordencompra: String,

    @SerializedName("id_TIPOTRANSACCION")
    var id_tipotransaccion: Tipotransaccion,

    @SerializedName("sector")
    var sector: Sector,

    @SerializedName("empleado")
    var empleado: Natural,

    @SerializedName("proveedor")
    var proveedor: Juridica,

    @SerializedName("items")
    var items: List<ItemOrdencompra>
)
