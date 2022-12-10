package com.example.mysplast

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.adapters.ItemTransferenciaAdapter
import com.example.mysplast.databinding.ActivityViewTransferenciaBinding
import com.example.mysplast.model.Transferencia
import com.example.mysplast.services.TransferenciaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

class ViewTransferenciaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  binding : ActivityViewTransferenciaBinding
    var mpref: SharedPreferences? = null
    var payload: String? = null
    private lateinit var itemTransferenciaAdapter: ItemTransferenciaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewTransferenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mpref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        val transaccionObtenida: Intent = intent
        val transaccionseleccionada =  transaccionObtenida.getStringExtra("idtransaccion")
        configuracionRecyclerView()
        obtenerTransferenciaProduccion(payload!!, transaccionseleccionada.toString())
    }

    private fun configuracionRecyclerView(){
        itemTransferenciaAdapter = ItemTransferenciaAdapter(this)
        binding.rcvItemTransferencia.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemTransferenciaAdapter
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    private fun obtenerTransferenciaProduccion(token: String, id: String){
        Log.d("ASDASDFADSFADSFAS","PRUEBAAAAAAAAAAAAAAAAAAAA")
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: TransferenciaService = retro.create(TransferenciaService::class.java)
        val call: Call<Transferencia> = pro.getTransferenciaxID("Bearer $token", id)
        call.enqueue(object : Callback<Transferencia> {
            override fun onResponse(
                call: Call<Transferencia>,
                response: Response<Transferencia>

            ) {

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = sdf.format(response.body()?.fechatran)
                var nroOrden: TextView = findViewById(R.id.edtItemNroTransferencia)
                var fecha: TextView = findViewById(R.id.edtItemFechaTransferencia)
                var dni: TextView = findViewById(R.id.edtItemDNITransferencia)
                var estado: TextView = findViewById(R.id.edtItemEstadoTransferencia)
                var responsable: TextView = findViewById(R.id.edtItemResponsableTransferencia)
                var almacenin: TextView = findViewById(R.id.edtItemAlmacenInTransferencia)
                var sectorin: TextView = findViewById(R.id.edtItemSectorInTransferencia)
                var almacensa: TextView = findViewById(R.id.edtItemAlmacenDestTransferencia)
                var sectorsa: TextView = findViewById(R.id.edtItemSectorDestTransferencia)
                var comentarios: TextView = findViewById(R.id.edtItemComentariosTransferencia)
                dni.text = response.body()?.id_PERSONA?.nrodoc
                nroOrden.text = response.body()?.nro_TRAN.toString()
                fecha.text = currentDate
                responsable.text = response.body()?.id_PERSONA?.nombres + " " +response.body()?.id_PERSONA?.ape_pat + " " + response.body()?.id_PERSONA?.ape_mat
                almacenin.text = response.body()?.id_sector?.id_almacen?.nom_almacen
                sectorin.text = response.body()?.id_sector?.nom_sector
                almacensa.text = response.body()?.id_sectordest?.id_almacen?.nom_almacen
                sectorsa.text = response.body()?.id_sectordest?.nom_sector
                comentarios.text = response.body()?.desc_TRAN
                itemTransferenciaAdapter.submitList(response.body()?.items)

                if(response.body()?.estado.equals("I")){
                    estado.text = "Inventariado"
                } else if(response.body()?.estado.equals("P")){
                    estado.text = "Pendiente"
                } else {
                    estado.text = "Anulado"
                }

            }

            override fun onFailure(call: Call<Transferencia>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}