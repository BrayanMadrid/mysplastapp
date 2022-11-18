package com.example.mysplast

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.adapters.ItemOrdenProdAdapter
import com.example.mysplast.databinding.ActivityProduccionBinding
import com.example.mysplast.databinding.ActivityViewOrdenPactivityBinding
import com.example.mysplast.model.Ordenprod
import com.example.mysplast.services.OrdenProdService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

class ViewOrdenPActivity : AppCompatActivity(), OnClickListener {

    private lateinit var  binding : ActivityViewOrdenPactivityBinding
    var mpref: SharedPreferences? = null
    var payload: String? = null
    private lateinit var itemOrdenProdAdapter: ItemOrdenProdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewOrdenPactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mpref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        val ordenObtenida: Intent = intent
        val ordenseleccionada =  ordenObtenida.getStringExtra("idordenprod")
        configuracionRecyclerView()
        obtenerOrdenProduccion(payload!!, ordenseleccionada.toString())
    }


    private fun obtenerOrdenProduccion(token: String, id: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.151.72:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: OrdenProdService = retro.create(OrdenProdService::class.java)
        val call: Call<Ordenprod> = pro.getOrdenxID("Bearer $token", id)
        call.enqueue(object : Callback<Ordenprod> {
            override fun onResponse(
                call: Call<Ordenprod>,
                response: Response<Ordenprod>

            ) {

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = sdf.format(response.body()?.fecha)
                var nroOrden:TextView = findViewById(R.id.edtItemNroOrdenProd)
                var fecha:TextView = findViewById(R.id.edtItemFecha)
                var estado:TextView = findViewById(R.id.edtItemEstado)
                var responsable:TextView = findViewById(R.id.edtItemResponsable)
                var almacenin:TextView = findViewById(R.id.edtItemAlmacenIn)
                var sectorin:TextView = findViewById(R.id.edtItemSectorIn)
                var almacensa:TextView = findViewById(R.id.edtItemAlmacenSa)
                var sectorsa:TextView = findViewById(R.id.edtItemSectorSa)
                var comentarios:TextView = findViewById(R.id.edtItemComentarios)
                nroOrden.text = response.body()?.nro_ordenprod.toString()
                fecha.text = currentDate
                responsable.text = response.body()?.id_PERSONA?.nombres + " " +response.body()?.id_PERSONA?.ape_pat + " " + response.body()?.id_PERSONA?.ape_mat
                almacenin.text = response.body()?.id_sector?.id_almacen?.nom_almacen
                sectorin.text = response.body()?.id_sector?.nom_sector
                almacensa.text = response.body()?.id_sectorinsumos?.id_almacen?.nom_almacen
                sectorsa.text = response.body()?.id_sectorinsumos?.nom_sector
                comentarios.text = response.body()?.desc_ordenprod
                itemOrdenProdAdapter.submitList(response.body()?.items)

                if(response.body()?.estado.equals("I")){
                    estado.text = "Inventariado"
                } else if(response.body()?.estado.equals("P")){
                    estado.text = "Pendiente"
                } else {
                    estado.text = "Anulado"
                }

            }

            override fun onFailure(call: Call<Ordenprod>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun configuracionRecyclerView(){
        itemOrdenProdAdapter = ItemOrdenProdAdapter(this)
        binding.rcvItemOrdenProd.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = itemOrdenProdAdapter
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}