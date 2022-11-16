package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class ItemOrdenprod(

    @SerializedName("id_ITEMORDENPROD")
    var id_itemordenprod: String,

    @SerializedName("cantidad")
    var cantidad: Double,

    @SerializedName("line")
    var line: Int,

    @SerializedName("id_PRODUCTO")
    var id_producto: Producto,
)
