package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Natural(

    @SerializedName("nombres")
    var nombres: String,

    @SerializedName("ape_PAT")
    var ape_pat: String,

    @SerializedName("ape_MAT")
    var ape_mat: String,

    @SerializedName("flag_TRABAJADOR")
    var flag_trabajador: String,

): Persona()
