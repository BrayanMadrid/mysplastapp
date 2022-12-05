package com.example.mysplast

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.adapters.ItemEgresoAdapter
import com.example.mysplast.databinding.ActivityViewEgresoBinding
import com.example.mysplast.model.Egreso
import com.example.mysplast.services.EgresoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

class ViewEgresoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  binding : ActivityViewEgresoBinding
    var mpref: SharedPreferences? = null
    var payload: String? = null
    private lateinit var itemEgresoAdapter: ItemEgresoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewEgresoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mpref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        val transaccionObtenida: Intent = intent
        val transaccionseleccionada =  transaccionObtenida.getStringExtra("idtransaccion")
        configuracionRecyclerView()
        obtenerEgresoProduccion(payload!!, transaccionseleccionada.toString())
    }

    private fun configuracionRecyclerView(){
        itemEgresoAdapter = ItemEgresoAdapter(this)
        binding.rcvItemEgreso.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = itemEgresoAdapter
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    private fun obtenerEgresoProduccion(token: String, id: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: EgresoService = retro.create(EgresoService::class.java)
        val call: Call<Egreso> = pro.getEgresoxID("Bearer $token", id)
        call.enqueue(object : Callback<Egreso> {
            override fun onResponse(
                call: Call<Egreso>,
                response: Response<Egreso>

            ) {

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = sdf.format(response.body()?.fechatran)
                var nroOrden: TextView = findViewById(R.id.edtItemNroEgreso)
                var fecha: TextView = findViewById(R.id.edtItemFechaEgreso)
                var estado: TextView = findViewById(R.id.edtItemEstadoEgreso)
                var responsable: TextView = findViewById(R.id.edtItemResponsableEgreso)
                var almacenin: TextView = findViewById(R.id.edtItemAlmacenInEgreso)
                var sectorin: TextView = findViewById(R.id.edtItemSectorInEgreso)
                var comentarios: TextView = findViewById(R.id.edtItemComentariosEgreso)
                var dni: TextView = findViewById(R.id.edtItemDNIEgreso)
                nroOrden.text = response.body()?.nro_TRAN.toString()
                dni.text = response.body()?.id_PERSONA?.nrodoc
                fecha.text = currentDate
                responsable.text = response.body()?.id_PERSONA?.nombres + " " +response.body()?.id_PERSONA?.ape_pat + " " + response.body()?.id_PERSONA?.ape_mat
                almacenin.text = response.body()?.id_sector?.id_almacen?.nom_almacen
                sectorin.text = response.body()?.id_sector?.nom_sector
                comentarios.text = response.body()?.desc_TRAN
                itemEgresoAdapter.submitList(response.body()?.items)

                if(response.body()?.estado.equals("I")){
                    estado.text = "Inventariado"
                } else if(response.body()?.estado.equals("P")){
                    estado.text = "Pendiente"
                } else {
                    estado.text = "Anulado"
                }

            }

            override fun onFailure(call: Call<Egreso>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}