package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Itemtransaccion(

    @SerializedName("id_ITEMTRANSACCION")
    var id_itemtransaccion: String,

    @SerializedName("cantidad")
    var cantidad: Double,

    @SerializedName("linea")
    var linea: Int,

    @SerializedName("lote")
    var lote: String,

    @SerializedName("id_PRODUCTO")
    var id_producto: Producto,

)
