package com.example.mysplast.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Producto(

    @SerializedName("id_producto")
    var id_producto: String,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("comentario")
    val comentario: String,

    @SerializedName("stockmin")
    val stockmin: Double,

    @SerializedName("id_categoria")
    val id_categoria: Categoria,

    @SerializedName("id_unmedida")
    val id_unmedida: Unmedida,

    @SerializedName("id_marca")
    val id_marca: Marca,

    @SerializedName("imagen")
    val imagen: String,

    @SerializedName("estado")
    val estado: String,

    @SerializedName("externalcode")
    val externalcode: String,

    @SerializedName("RegisterUser")
    val registerUser: String,

    @SerializedName("RegisterDate")
    val registerDate: Date,

    @SerializedName("XLastUser")
    val xLastUser: String,

    @SerializedName("XLasDate")
    val xLasDate: Date,

    @SerializedName("ActivationUser")
    val activationUser: String,

    @SerializedName("ActivationDate")
    val activationDate: Date,
)
