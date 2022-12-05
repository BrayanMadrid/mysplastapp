package com.example.mysplast.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mysplast.MainActivity
import com.example.mysplast.R
import com.example.mysplast.model.Payload
import com.example.mysplast.model.Token
import com.example.mysplast.services.ApiService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.StandardCharsets

class LoginActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    var payloadtokeeeen: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var btnLogearse = findViewById<Button>(R.id.btnLogearse)
        btnLogearse.setOnClickListener{
            iniciarSesion()
        }

    }

    private fun iniciarSesion() {
        var edtUsuario = findViewById<EditText>(R.id.edtUsuario).text.toString()
        var edtPassword = findViewById<EditText>(R.id.edtContraseña).text.toString()
        if (edtUsuario.isEmpty() || edtPassword.isEmpty()){
            val texto = "Todos los campos son obligatorios!"
            val duracion = Toast.LENGTH_LONG
            val toast = Toast.makeText(applicationContext, texto, duracion)
            toast.show()
    } else {
            var call = apiService.autenticarse(
                "Basic YW5ndWxhcmFwcDoxMjM0NQ==",
                edtUsuario,
                edtPassword,
                "password"
            )
            call.enqueue(object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.isSuccessful) {
                        var respuesta = response.body()
                        if (respuesta == null) {
                            Toast.makeText(
                                applicationContext,
                                "Error en el Servidor",
                                Toast.LENGTH_LONG
                            ).show()
                            return
                        }

                        if (respuesta.accesstoken != null) {
                            val mpref = getSharedPreferences("token", MODE_PRIVATE)
                            val prefusuario = getSharedPreferences("usuario", MODE_PRIVATE)
                            var editorusuario = prefusuario.edit()
                            var editor = mpref.edit()
                            editor.putString("accesstoken", respuesta.accesstoken)
                            editor.commit()



                            payloadtokeeeen = (respuesta.accesstoken).split("\\.".toRegex())[1]
                            var objeto = Base64.decode(payloadtokeeeen, Base64.DEFAULT)
                            var texto: String = String(objeto, StandardCharsets.UTF_8)
                            var g = Gson()
                            var payload: Payload = g.fromJson(texto, Payload::class.java)

                            editorusuario.putString("accesstoken", payload.user_name)
                            editorusuario.commit()
                            val intent =  Intent(applicationContext, MainActivity::class.java).apply {
                                putExtra("username",  payload.user_name)
                                putExtra("correo",payload.correo)
                                putExtra("nombre",payload.nombre)
                            }
                            startActivity(intent)
                        } else {
                                Log.d("ERRROOOOOORRR", "Inicio de Ssion incorrecto ")
                        }
                    } else {
                        val texto = "Error, los datos ingresados son erroneos!"
                        val duracion = Toast.LENGTH_LONG
                        val toast = Toast.makeText(applicationContext, texto, duracion)
                        toast.show()
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Log.d("ERRROOOOOORRR", "Datos Incorrectos " + t.message)
                }

            })

        }
    }
}