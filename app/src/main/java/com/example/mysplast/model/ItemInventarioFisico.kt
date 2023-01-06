package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class ItemInventarioFisico(

    @SerializedName("id")
    var id: Long,

    @SerializedName("cantidad")
    var cantidad: Double,

    @SerializedName("observacion")
    var observacion: String,

    @SerializedName("cantidadsistema")
    var cantidadsistema: Double,

    @SerializedName("diferencia")
    var diferencia: Double,

    @SerializedName("id_PRODUCTO")
    var id_producto: Producto,

)
