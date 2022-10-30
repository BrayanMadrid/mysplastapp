package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

 data class Token (

    @SerializedName("access_token")
    var accesstoken: String,

    @SerializedName("refresh_token")
    var refreshToken: String,

    )


