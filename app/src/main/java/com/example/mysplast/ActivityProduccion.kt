package com.example.mysplast

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.adapters.ListadoOrdenesProdAdapter
import com.example.mysplast.databinding.ActivityProduccionBinding
import com.example.mysplast.model.Almacen
import com.example.mysplast.model.Ordenprod
import com.example.mysplast.model.Sector
import com.example.mysplast.services.AlmacenService
import com.example.mysplast.services.OrdenProdService
import com.example.mysplast.services.SectorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ActivityProduccion() : AppCompatActivity(), OnClickListener {

    private lateinit var  binding : ActivityProduccionBinding
    var mpref: SharedPreferences? = null
    var payload: String? = null
    private lateinit var listAdapterOrdenProds: ListadoOrdenesProdAdapter
    private var almacen: String? = ""
    private var sector: String? = ""
    lateinit var fechaInAE: EditText
    lateinit var fechaFinAE: EditText
    lateinit var spalmacen: Spinner
    lateinit var spsector: Spinner
    lateinit var btnConsultarOP: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProduccionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mpref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        fechaInAE =  findViewById(R.id.edtFechaInAI)
        fechaFinAE = findViewById(R.id.edtFechaFinAI)
        spalmacen = findViewById(R.id.spalmacenxi)
        spsector = findViewById(R.id.spsectorxi)
        btnConsultarOP = findViewById(R.id.btnConsultarOP)
        btnConsultarOP.setOnClickListener {

            var almacenSeleccionado: String =  almacen.toString()
            var sectorSeleccionado: String =  sector.toString()
            var fechaInicioSeleccionada: String = fechaInAE.text.toString()
            var fechaFinalSeleccionada: String  = fechaFinAE.text.toString()
            buscarOrdenesProduccion(payload!!,sectorSeleccionado, almacenSeleccionado, fechaInicioSeleccionada, fechaFinalSeleccionada,"")

        }
        fechaInAE.setOnClickListener(View.OnClickListener {
            CargarFechaIn()
        })
        fechaFinAE.setOnClickListener(View.OnClickListener {
            CargarFechaFin()
        })

        spalmacen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val a: Almacen = parent.getItemAtPosition(position) as Almacen
                almacen = a.id_almacen
                obtenerSectores(payload!!, a.id_almacen)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spsector.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val s: Sector = parent.getItemAtPosition(position) as Sector
                sector = s.id_sector
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        obtenerAlmacenes(payload!!)
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

    // fin create
    private fun CargarFechaIn() {
        val calendar = Calendar.getInstance()
        val YEAR = calendar[Calendar.YEAR]
        val MONTH = calendar[Calendar.MONTH]
        val DATE = calendar[Calendar.DATE]
        val datePickerDialog = DatePickerDialog(this,
            { datePicker, year, month, date ->
                fechaInAE?.setText("")
                val calendar1 = Calendar.getInstance()
                calendar1[Calendar.YEAR] = year
                calendar1[Calendar.MONTH] = month
                calendar1[Calendar.DATE] = date
                fechaInAE?.setText(DateFormat.format("dd-MM-yyyy", calendar1))
            }, YEAR, MONTH, DATE
        )
        datePickerDialog.show()
    }

    private fun CargarFechaFin() {
        val calendar = Calendar.getInstance()
        val YEAR = calendar[Calendar.YEAR]
        val MONTH = calendar[Calendar.MONTH]
        val DATE = calendar[Calendar.DATE]
        val datePickerDialog = DatePickerDialog(this,
            { datePicker, year, month, date ->
                fechaFinAE?.setText("")
                val calendar1 = Calendar.getInstance()
                calendar1[Calendar.YEAR] = year
                calendar1[Calendar.MONTH] = month
                calendar1[Calendar.DATE] = date
                fechaFinAE?.setText(DateFormat.format("dd-MM-yyyy", calendar1))
            }, YEAR, MONTH, DATE
        )
        datePickerDialog.show()
    }

    private fun obtenerSectores(token: String, almacen: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: SectorService = retro.create(SectorService::class.java)
        val call: Call<ArrayList<Sector>> = pro.getObtenerSectorxAlmacen("Bearer $token", almacen)
        call.enqueue(object : Callback<ArrayList<Sector>> {
            override fun onResponse(
                call: Call<ArrayList<Sector>>,
                response: Response<ArrayList<Sector>>

            ) {
                if(response.isSuccessful()){
                    var sub: ArrayList<Sector>
                    sub = response.body()!!
                    val adapter: ArrayAdapter<Sector> = ArrayAdapter<Sector>(
                        binding.root.context,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        sub
                    )
                    spsector.setAdapter(adapter)
                }

            }

            override fun onFailure(call: Call<ArrayList<Sector>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun obtenerAlmacenes(token: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: AlmacenService = retro.create(AlmacenService::class.java)
        val call: Call<ArrayList<Almacen>> = pro.getListadoAlmacenes("Bearer $token")
        call.enqueue(object : Callback<ArrayList<Almacen>> {
            override fun onResponse(
                call: Call<ArrayList<Almacen>>,
                response: Response<ArrayList<Almacen>>

            ) {
                if (response.isSuccessful()) {
                    var al: ArrayList<Almacen>
                    al = response.body()!!
                    val adapter = ArrayAdapter(
                        binding.root.context,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        al
                    )
                    spalmacen.adapter = adapter

                }
            }
            override fun onFailure(call: Call<ArrayList<Almacen>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    private fun buscarOrdenesProduccion(token: String, sector: String, almacen: String, fecha1: String, fecha2: String, estado: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: OrdenProdService = retro.create(OrdenProdService::class.java)
        val call: Call<ArrayList<Ordenprod>> = pro.getFiltroOrdenesProd("Bearer $token", sector, almacen, fecha1, fecha2, estado)
        call.enqueue(object : Callback<ArrayList<Ordenprod>> {
            override fun onResponse(
                call: Call<ArrayList<Ordenprod>>,
                response: Response<ArrayList<Ordenprod>>

            ) {
                if(response.isSuccessful()){
                    listAdapterOrdenProds.notifyDataSetChanged()
                    listAdapterOrdenProds.submitList(response.body())
                }

            }

            override fun onFailure(call: Call<ArrayList<Ordenprod>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}