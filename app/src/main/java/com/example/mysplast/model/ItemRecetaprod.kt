package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class ItemRecetaprod(

    @SerializedName("id_ITEMRECETAPROD")
    var id_itemrecetaprod: String,

    @SerializedName("cantidad")
    var cantidad: Double,

    @SerializedName("id_PRODUCTO")
    var id_producto: Producto,
)
