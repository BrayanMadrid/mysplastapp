package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

data class Usuario_roles(

    @SerializedName("usuario_id")
    var usuario_id: Int,
    @SerializedName("roles_id")
    var roles_id: Int,
)
