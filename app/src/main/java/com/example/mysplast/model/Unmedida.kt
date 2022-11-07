package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Unmedida(

    @SerializedName("id_unmedida")
    private var id_unmedida: String,

    @SerializedName("nom_unmedida")
    private var nom_unmedida: String,

    @SerializedName("reg_user")
    private var reg_user: String,

    @SerializedName("fech_reg_user")
    private var fech_reg_user: String,
)
