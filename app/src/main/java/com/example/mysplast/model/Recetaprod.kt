package com.example.mysplast.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Recetaprod(

    @SerializedName("id_RECETA")
    var id_receta: String,

    @SerializedName("nro_RECETA")
    var nro_receta: String,

    @SerializedName("nom_RECETA")
    var nom_receta: String,

    @SerializedName("fecha")
    var fecha: Date,

    @SerializedName("estado")
    var estado: String,

    @SerializedName("id_PRODUCTO")
    var id_producto: Producto,

    @SerializedName("items")
    var items: List<ItemRecetaprod>,
)
