package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class ItemOrdencompra(

    @SerializedName("id_ITEMORDENCOMPRA")
    var id_itemordencompra: String,

    @SerializedName("cantidad")
    var cantidad: Double,

    @SerializedName("precio")
    var precio: Double,

    @SerializedName("total")
    var total: Double,

    @SerializedName("line")
    var line: Int,

    @SerializedName("id_PRODUCTO")
    var id_producto: Producto,

)
