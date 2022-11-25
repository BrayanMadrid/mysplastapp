package com.example.mysplast.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Ingreso(

    @SerializedName("nro_ORDEN")
    var nro_orden: String,

    @SerializedName("guia_REF")
    var guia_ref: String,

    @SerializedName("fecha_INGRESO")
    var fecha_ingreso: Date,

    @SerializedName("proveedor")
    var proveedor: Juridica,

): Transaccion()
