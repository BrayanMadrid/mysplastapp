package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Categoria(

    @SerializedName("id_categoria")
    private var id_categoria: String,

    @SerializedName("nom_categoria")
    private var nom_categoria: String,

    @SerializedName("reg_user")
    private var reg_user: String,

    @SerializedName("fech_reg_user")
    private var fech_reg_user: String,

)
