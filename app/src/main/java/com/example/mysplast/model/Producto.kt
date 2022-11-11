package com.example.mysplast.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Producto(

    @SerializedName("id_PRODUCTO")
    var id_producto: String,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("comentario")
    val comentario: String,

    @SerializedName("stockmin")
    val stockmin: Double,

    @SerializedName("id_CATEGORIA")
    val id_categoria: Categoria,

    @SerializedName("id_UNMEDIDA")
    val id_unmedida: Unmedida,

    @SerializedName("id_MARCA")
    val id_marca: Marca,

    @SerializedName("flag_PRODUCCION")
    val flag_produccion : String,

    @SerializedName("flag_INSUMO")
    val flag_insumo: String,

    @SerializedName("imagen")
    val imagen: String,

    @SerializedName("estado")
    val estado: String,

    @SerializedName("codexterno")
    val codexterno: String,
)
