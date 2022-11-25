package com.example.mysplast

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysplast.adapters.ListadoTransferenciasAdapter
import com.example.mysplast.databinding.ActivityTransferenciasBinding
import com.example.mysplast.model.Almacen
import com.example.mysplast.model.Sector
import com.example.mysplast.model.Transferencia
import com.example.mysplast.services.AlmacenService
import com.example.mysplast.services.SectorService
import com.example.mysplast.services.TransferenciaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ActivityTransferencias : AppCompatActivity(), View.OnClickListener {

    lateinit var fechaInTransferencia: EditText
    lateinit var fechaFinTransferencia: EditText
    private lateinit var  binding : ActivityTransferenciasBinding
    var mpref: SharedPreferences? = null
    var payload: String? = null
    private lateinit var listadoTransferenciaAdapter: ListadoTransferenciasAdapter
    private var almacen: String? = ""
    private var sector: String? = ""
    lateinit var spalmacen: Spinner
    lateinit var spsector: Spinner
    lateinit var btnConsultarTransferencia: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferenciasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mpref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        payload = mpref?.getString("accesstoken","")
        fechaInTransferencia =  findViewById(R.id.edtFechaInTransferencia)
        fechaFinTransferencia = findViewById(R.id.edtFechaFinTransferencia)

        spalmacen = findViewById(R.id.spalmacenTransferencia)
        spsector = findViewById(R.id.spsectorTransferencia)
        btnConsultarTransferencia = findViewById(R.id.btnConsultarTransferencia)
        btnConsultarTransferencia.setOnClickListener {

            var almacenSeleccionado: String =  almacen.toString()
            var sectorSeleccionado: String =  sector.toString()
            var fechaInicioSeleccionada: String = fechaInTransferencia.text.toString()
            var fechaFinalSeleccionada: String  = fechaFinTransferencia.text.toString()
            buscarTransferencias(payload!!,sectorSeleccionado, almacenSeleccionado, fechaInicioSeleccionada, fechaFinalSeleccionada)

        }

        fechaInTransferencia.setOnClickListener(View.OnClickListener {
            CargarFechaIn()
        })
        fechaFinTransferencia.setOnClickListener(View.OnClickListener {
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
        listadoTransferencias(payload!!)
    }

    private fun CargarFechaIn() {
        val calendar = Calendar.getInstance()
        val YEAR = calendar[Calendar.YEAR]
        val MONTH = calendar[Calendar.MONTH]
        val DATE = calendar[Calendar.DATE]
        val datePickerDialog = DatePickerDialog(this,
            { datePicker, year, month, date ->
                fechaInTransferencia?.setText("")
                val calendar1 = Calendar.getInstance()
                calendar1[Calendar.YEAR] = year
                calendar1[Calendar.MONTH] = month
                calendar1[Calendar.DATE] = date
                fechaInTransferencia?.setText(DateFormat.format("dd-MM-yyyy", calendar1))
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
                fechaFinTransferencia?.setText("")
                val calendar1 = Calendar.getInstance()
                calendar1[Calendar.YEAR] = year
                calendar1[Calendar.MONTH] = month
                calendar1[Calendar.DATE] = date
                fechaFinTransferencia?.setText(DateFormat.format("dd-MM-yyyy", calendar1))
            }, YEAR, MONTH, DATE
        )
        datePickerDialog.show()
    }


    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


    private fun configuracionRecyclerView(){
        listadoTransferenciaAdapter = ListadoTransferenciasAdapter(this)
        binding.rcvTransferencia.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listadoTransferenciaAdapter
        }
    }

    private fun listadoTransferencias(token: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: TransferenciaService = retro.create(TransferenciaService::class.java)
        val call: Call<ArrayList<Transferencia>> = pro.getListadoTransferencia("Bearer $token")
        call.enqueue(object : Callback<ArrayList<Transferencia>> {
            override fun onResponse(
                call: Call<ArrayList<Transferencia>>,
                response: Response<ArrayList<Transferencia>>

            ) {
                listadoTransferenciaAdapter.submitList(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Transferencia>>, t: Throwable) {

            }

        })
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


    private fun buscarTransferencias(token: String, sector: String, almacen: String, fecha1: String, fecha2: String){
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()
        val pro: TransferenciaService = retro.create(TransferenciaService::class.java)
        val call: Call<ArrayList<Transferencia>> = pro.getFiltroTransferencia("Bearer $token", sector, almacen, fecha1, fecha2)
        call.enqueue(object : Callback<ArrayList<Transferencia>> {
            override fun onResponse(
                call: Call<ArrayList<Transferencia>>,
                response: Response<ArrayList<Transferencia>>

            ) {
                if(response.isSuccessful()){
                    listadoTransferenciaAdapter.notifyDataSetChanged()
                    listadoTransferenciaAdapter.submitList(response.body())
                }

            }

            override fun onFailure(call: Call<ArrayList<Transferencia>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}