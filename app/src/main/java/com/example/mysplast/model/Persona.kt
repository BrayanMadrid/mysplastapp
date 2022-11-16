package com.example.mysplast.model

import com.google.gson.annotations.SerializedName

 abstract class Persona(

){
     @SerializedName("id_PERSONA")
     lateinit var id_persona: String

     @SerializedName("nrodoc")
     lateinit var nrodoc: String

     @SerializedName("telefono")
     lateinit var telefono: String

     @SerializedName("correo")
     lateinit var correo: String

     @SerializedName("direccion")
     lateinit var direccion: String

     @SerializedName("estado")
     lateinit var estado: String

     @SerializedName("id_DISTRITO")
     lateinit var id_distrito: Distrito

     @SerializedName("id_TIPODOC")
     lateinit var id_tipodoc: Tipodoc
 }

