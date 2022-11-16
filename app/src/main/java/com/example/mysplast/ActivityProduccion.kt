package com.example.mysplast

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mysplast.model.Natural
import com.example.mysplast.model.Ordenprod
import com.example.mysplast.services.NaturalService
import com.example.mysplast.services.OrdenProdService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityProduccion : AppCompatActivity() {

    var mpref: SharedPreferences? = null
    var payload: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produccion)
        mpref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        listadoOrdenProduccion(payload!!)
    }

    private fun listadoOrdenProduccion(token: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: OrdenProdService = retro.create(OrdenProdService::class.java)
        val call: Call<List<Ordenprod>> = pro.getListadoOrdenesProduccion("Bearer $token")
        call.enqueue(object : Callback<List<Ordenprod>> {
            override fun onResponse(
                call: Call<List<Ordenprod>>,
                response: Response<List<Ordenprod>>

            ) {
                Log.d("ORDENPRODUCIOOOON", response.body()?.get(0)?.nro_ordenprod.toString())
                Log.d("ORDENPRODUCIOOOON", response.body()?.get(1)?.nro_ordenprod.toString())
                Log.d("ORDENPRODUCIOOOON", response.body()?.get(2)?.nro_ordenprod.toString())
            }

            override fun onFailure(call: Call<List<Ordenprod>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}