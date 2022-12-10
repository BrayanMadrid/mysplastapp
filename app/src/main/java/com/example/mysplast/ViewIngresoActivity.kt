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
import com.example.mysplast.adapters.ItemIngresoAdapter
import com.example.mysplast.databinding.ActivityViewIngresoBinding
import com.example.mysplast.model.Ingreso
import com.example.mysplast.services.IngresoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

class ViewIngresoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  binding : ActivityViewIngresoBinding
    var mpref: SharedPreferences? = null
    var payload: String? = null
    private lateinit var itemIngresoAdapter: ItemIngresoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewIngresoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mpref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        val transaccionObtenida: Intent = intent
        val transaccionseleccionada =  transaccionObtenida.getStringExtra("idtransaccion")
        Log.d("TRANSACION",transaccionseleccionada.toString())
        configuracionRecyclerView()
        obtenerIngresoProduccion(payload!!, transaccionseleccionada.toString())
    }

    private fun configuracionRecyclerView(){
        itemIngresoAdapter = ItemIngresoAdapter(this)
        binding.rcvItemIngreso.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemIngresoAdapter
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    private fun obtenerIngresoProduccion(token: String, id: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: IngresoService = retro.create(IngresoService::class.java)
        val call: Call<Ingreso> = pro.getIngresoxID("Bearer $token", id)
        call.enqueue(object : Callback<Ingreso> {
            override fun onResponse(
                call: Call<Ingreso>,
                response: Response<Ingreso>

            ) {

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = sdf.format(response.body()?.fechatran)
                var nroOrden: TextView = findViewById(R.id.edtItemNroIngreso)
                var dni: TextView = findViewById(R.id.edtItemDNIIngreso)
                var fecha: TextView = findViewById(R.id.edtItemFechaIngreso)
                var estado: TextView = findViewById(R.id.edtItemEstadoIngreso)
                var responsable: TextView = findViewById(R.id.edtItemResponsableIngreso)
                var almacenin: TextView = findViewById(R.id.edtItemAlmacenInIngreso)
                var sectorin: TextView = findViewById(R.id.edtItemSectorInIngreso)
                var comentarios: TextView = findViewById(R.id.edtItemComentariosIngreso)
                nroOrden.text = response.body()?.nro_TRAN.toString()
                fecha.text = currentDate
                dni.text = response.body()?.id_PERSONA?.nrodoc
                responsable.text = response.body()?.id_PERSONA?.nombres + " " +response.body()?.id_PERSONA?.ape_pat + " " + response.body()?.id_PERSONA?.ape_mat
                almacenin.text = response.body()?.id_sector?.id_almacen?.nom_almacen
                sectorin.text = response.body()?.id_sector?.nom_sector
                comentarios.text = response.body()?.desc_TRAN
                itemIngresoAdapter.submitList(response.body()?.items)
                Log.d("PRODUCTO",response.body()?.items?.get(0)?.id_producto?.nombre.toString())
                if(response.body()?.estado.equals("I")){
                    estado.text = "Inventariado"
                } else if(response.body()?.estado.equals("P")){
                    estado.text = "Pendiente"
                } else {
                    estado.text = "Anulado"
                }

            }

            override fun onFailure(call: Call<Ingreso>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}