package com.example.mysplast

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.adapters.ListadoOrdenesProdAdapter
import com.example.mysplast.databinding.ActivityProduccionBinding
import com.example.mysplast.model.Ordenprod
import com.example.mysplast.services.OrdenProdService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityProduccion : AppCompatActivity(), OnClickListener {

    private lateinit var  binding : ActivityProduccionBinding
    var mpref: SharedPreferences? = null
    var payload: String? = null
    private lateinit var listAdapterOrdenProds: ListadoOrdenesProdAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProduccionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mpref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        configuracionRecyclerView()
        listadoOrdenProduccion(payload!!)
    }

    private fun configuracionRecyclerView(){
        listAdapterOrdenProds = ListadoOrdenesProdAdapter(this)
        binding.rcvOrdenProd.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapterOrdenProds
        }
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
                listAdapterOrdenProds.submitList(response.body())
            }

            override fun onFailure(call: Call<List<Ordenprod>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}