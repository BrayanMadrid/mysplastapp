package com.example.mysplast

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateFormat
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

//Actividad donde se muestra el listado y filtro para las ordenes de producción
class ActivityProduccion() : AppCompatActivity(), OnClickListener {

    //inicializamos una valiable llamada binding del tipo ActivityProduccionBinding (hace referencia a la vista de la actividad producción)
    private lateinit var  binding : ActivityProduccionBinding

    //creamos una variable llamada mpref del tipo SharedPreferences (datos alojados temporalmente en el app como usuarios, contraseñas o tókens) y lo inicializamos en null
    var mpref: SharedPreferences? = null
    //creamos una variable lamada payload de tipo String.
    var payload: String? = null
    //Inicializamos el adaptador ListadoOrdenesProdAdapter al cual se le mandará e listado de Ordenes de Producción para su visualización en el RecyclerView
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
        //Le asignamos a la variable binding la vista de la actividad, en este caso ActivityProduccionBinding y su correspondiente xml
        binding = ActivityProduccionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //le pasamos el valor almacenado temporalmente en el app (token) para poder realizar uso del mismo en las cunsultas del activity al servidor spring
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

    //Creamos el método para poder realizar la configuración inicial del adapter que usaremos en esta actividad
    private fun configuracionRecyclerView(){

        listAdapterOrdenProds = ListadoOrdenesProdAdapter(this)
        //utiizamos el recyclerview de la actividad identificado con el id rcvOrdenProd al cual le asignaremos el adapter ListadoOrdenesProdAdapter
        binding.rcvOrdenProd.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapterOrdenProds
        }
    }

    //metodo que realizará la petición del listado de ordenes de producción al servidor de spring
    private fun listadoOrdenProduccion(token: String){
        //Se crea una variable llamada retro a cual se le asignará la base de a URL y como respuesta se dará el formato Json.
        val retro = Retrofit.Builder().baseUrl("http://192.168.3.36:8080").addConverterFactory(
            GsonConverterFactory.create()).build()

        //Se crea una variable llamada pro del tipo OrdenProService (Interface donde se encuentran los métodos a usar de la OrdenprodController de Spring)
        val pro: OrdenProdService = retro.create(OrdenProdService::class.java)

        //Se crea una variable llamada call de tipo Call de retrofit al cual le indicaremos que la respuesta a la llamada que recibiremos será una lista del tipo Ordenprd
        //Luego llamamos a la variable pro que sería la interface OrdenProdService  y llamamos al método listadoOrdenesProducción el cual necesita el parámetro tóken
        val call: Call<List<Ordenprod>> = pro.getListadoOrdenesProduccion("Bearer $token")
        call.enqueue(object : Callback<List<Ordenprod>> {
            override fun onResponse(
                //realizamos la llamada
                call: Call<List<Ordenprod>>,
                //obtenemos la respuesta del tipo lista de orden de producción
                response: Response<List<Ordenprod>>

            ) {
                //enviamos la lista al adapter para generar y alimentar el RecyclerView
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