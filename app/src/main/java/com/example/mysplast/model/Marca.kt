package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Marca(

    @SerializedName("id_marca")
    private var id_marca: String,

    @SerializedName("nom_marca")
    private var nom_marca: String,

    @SerializedName("reg_user")
    private var reg_user: String,

    @SerializedName("fech_reg_user")
    private var fech_reg_user: String,

)
