package com.example.mysplast.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysplast.R
import com.example.mysplast.ViewOrdenPActivity
import com.example.mysplast.databinding.ListadoOrdenesprodBinding
import com.example.mysplast.model.Ordenprod
import java.text.SimpleDateFormat

//Adapter que replicará  las ordenes de de producción (layout activity_producción)

// Creamos la clase ListadoOrdenesProdAdapter el cual recibirá como valor listener del tipo View.OnClickListener el cual permitirá generar una interación con el adapter (cards)
class ListadoOrdenesProdAdapter  (private val listener: OnClickListener):
    ListAdapter<Ordenprod, RecyclerView.ViewHolder>(OrdenDiffCallback()){

    // El adapter utiizará una lista del tipo Ordenprod (Clase que hace referencia a la Orden de Producción) y también un ViewHolder (el item de la vista)

    //Inicializamos la variable context que es del tipo Context

    private lateinit var context: Context

    //Método autoimplementado desde el RecyclerView.ViewHolder para indicar el contexto y la vista (layout - xml) que hará uso el adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Se le asigna a la variable context el valor del contexto del parent (ViewGroup)
        context = parent.context

        //Se crea un valor llamado view al cual se le asignará el layout - xml llamado listado_ordenesprod para que lo pueda replicar en cada uno de los items de la lista
        val view = LayoutInflater.from(context).inflate(R.layout.listado_ordenesprod,parent,false)
        //Retornamos el ViewHolder pasándole el valor view
        return ViewHolder(view)
    }

    //Método autoimplementado desde el RecyclerView.ViewHolder donde se asignarán las propiedades de los componentes del layout seleccionado
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //Creamos un valor llamado itemOrdenprod al cual se le asignará la posición del recyclerview (el item en la posición seleccionada de la lista)
        val obtenerOrdenprod = getItem(position)
        with(holder as ViewHolder){

            //El binding hace referencia a la vista seleccionada en el método ViewHolder, en este caso el xml listado_ordenprod el cual permitirá
            //seleccionar un componente (EditText, TextView, Spinner, Buttons, ImageViews, etc...) y asignarles modelos de datos o darles dinamísmo.

            //En este caso obtenemos el componente edtLinea(EditText del Layout listado_ordenprod) y le mandamos como texto (contenido) el dato de la lína de la clase
            //Ordenprod (orden de producción)

            //creamos un valor sdf al cual se le asignará un tipo de formato fecha (dia mes y año)
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            //creamos un valor currentDate a cual se le asignará la fecha del la ordenProducción formateado según lo indicado en la línea superior
            val currentDate = sdf.format(obtenerOrdenprod.fecha)
            binding.edtNroOrdenProd.text = obtenerOrdenprod.nro_ordenprod
            binding.edtComentarios.text = obtenerOrdenprod.desc_ordenprod
            binding.edtFecha.text = currentDate.toString()
            binding.edtResponsable.text = obtenerOrdenprod.id_PERSONA.nombres + " " +obtenerOrdenprod.id_PERSONA.ape_pat + " " + obtenerOrdenprod.id_PERSONA.ape_mat
            binding.edtAlmacenIn.text = obtenerOrdenprod.id_sector.id_almacen.nom_almacen
            binding.edtSectorIn.text = obtenerOrdenprod.id_sector.nom_sector
            binding.edtAlmacenSa.text = obtenerOrdenprod.id_sectorinsumos.id_almacen.nom_almacen
            binding.edtSectorSa.text = obtenerOrdenprod.id_sectorinsumos.nom_sector

            //Se realiza la validación según el dato enviado (estado) desde la base de datos, el cual cambiará el valor del texto enviado para el edtEstado
            if(obtenerOrdenprod.estado.equals("I")){
                binding.edtEstado.text = "Inventariado"
            } else if(obtenerOrdenprod.estado.equals("P")){
                binding.edtEstado.text = "Pendiente"
            } else {
                binding.edtEstado.text = "Anulado"
            }

            //declaramos el valor btnVisualizarOrden del tipo Button al cual le asignamos el componente btnVisualizarOrdenProd (Botón Visualizar)
            //del layout listado_ordenprod.
            val btnVisualizarOrden: Button = binding.btnVisualizarOrdenProd
            //utilizamos el método setOnClickListener desde el botón declarado para indicarle qué acciones realizará al momento de seleccionarlo
            btnVisualizarOrden.setOnClickListener {

                //Creamos un valor intent del tipo Intent (El cual te permite lanzar actividades), al cual le asignaremos la actividad que lanzaremos al realizar click en el botón
                //visualizar, le pasamos el contexto que haría referencia a la actividad actual donde estamos, luego la actividad a la cual nos queremos dirigir ViewOrdenPActivity
                //luego agregamos el .apply para realizar acciones al momento de realizar la transición de actividad
                val intent: Intent =  Intent(context, ViewOrdenPActivity::class.java).apply {
                    //En este caso enviaremos una variable llamada idordenprod desde esta actividad hacia la que queremos lanzar ViewOrdenPActivity y como dato le enviaremos
                    // el id de la orden de producción seleccionada o alojada en el cardview
                    putExtra("idordenprod",  obtenerOrdenprod.id_ordenprod)
                }
                //lanzamos la actividad
                context.startActivity(intent)

            }
        }
    }

    class OrdenDiffCallback: DiffUtil.ItemCallback<Ordenprod>(){
        override fun areItemsTheSame(oldItem: Ordenprod, newItem: Ordenprod): Boolean  = oldItem.id_ordenprod == newItem.id_ordenprod

        override fun areContentsTheSame(oldItem: Ordenprod, newItem: Ordenprod): Boolean  = oldItem == newItem

    }

    //Método donde asignamos un componente visual en este caso ListadoOrdenesprodBinding el cual hace referencia al layout-xml -> listado_ordenprod
    // el cual permitirá utilizar los componentes de la vista selleccionada
    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoOrdenesprodBinding.bind(view)


    }



}