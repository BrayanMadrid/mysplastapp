package com.example.mysplast.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysplast.R
import com.example.mysplast.databinding.ListadoItemOrdenprodBinding
import com.example.mysplast.model.ItemOrdenprod

//Adapter que replicará el detale de las ordenes de de producción (layout activity_view_orden_pactivity)

// Creamos la clase ItemOrdenProdAdapter el cual recibirá como valor listener del tipo View.OnClickListener el cual permitirá generar una interación con el adapter (cards)
class ItemOrdenProdAdapter(private val listener: View.OnClickListener):
    ListAdapter<ItemOrdenprod, RecyclerView.ViewHolder>(ItemOrdenProdAdapter.OrdenDiffCallback()) {
// El adapter utiizará una lista del tipo ItemOrdenprod (Clase que hace referencia al detalle de la Orden de Producción) y también un ViewHolder (el item de la vista)

    //Inicializamos la variable context que es del tipo Context
    private lateinit var context: Context


    //Método autoimplementado desde el RecyclerView.ViewHolder para indicar el contexto y la vista (layout - xml) que hará uso el adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Se le asigna a la variable context el valor del contexto del parent (ViewGroup)
        context = parent.context
        //Se crea un valor llamado view al cual se le asignará el layout - xml llamado listado_item_ordenprod para que lo pueda replicar en cada uno de los items de la lista
        val view = LayoutInflater.from(context).inflate(R.layout.listado_item_ordenprod,parent,false)
        //Retornamos el ViewHolder pasándole el valor view
        return ViewHolder(view)
    }

    //Método autoimplementado desde el RecyclerView.ViewHolder donde se asignarán las propiedades de los componentes del layout seleccionado
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Creamos un valor llamado itemOrdenprod al cual se le asignará la posición del recyclerview (el item en la posición seleccionada de la lista)
        val itemOrdenprod = getItem(position)
        with(holder as ViewHolder){

            //El binding hace referencia a la vista seleccionada en el método ViewHolder, en este caso el xml listado_item_ordenprod el cual permitirá
            //seleccionar un componente (EditText, TextView, Spinner, Buttons, ImageViews, etc...) y asignarles modelos de datos o darles dinamísmo.

            //En este caso obtenemos el componente edtLinea(EditText del Layout listado_item_ordenprod) y le mandamos como texto (contenido) el dato de la lína de la clase
            //itemOrdenprod (detalle de la orden de producción)
            binding.edtLinea.text = itemOrdenprod.line.toString()
            binding.edtProductoOrdenProd.text = itemOrdenprod.id_producto.nombre.toString()
            binding.edtCantidadOrdenProd.text = itemOrdenprod.cantidad.toString()

        }
    }


    //Método donde se reaizará la validación y comparación entre items de la lista obtenida
    class OrdenDiffCallback: DiffUtil.ItemCallback<ItemOrdenprod>(){

        //En este caso comparamos items de la clase ItemOrdenprod por id y comparamos, en caso de que encuentre semejanza reemplaza el item antiguo por el nuevo para
        //evitar duplicidad de información
        override fun areItemsTheSame(oldItem: ItemOrdenprod, newItem: ItemOrdenprod): Boolean  = oldItem.id_itemordenprod == newItem.id_itemordenprod

        override fun areContentsTheSame(oldItem: ItemOrdenprod, newItem: ItemOrdenprod): Boolean  = oldItem == newItem

    }

    //Método donde asignamos un componente visual en este caso ListadoItemOrdenprodBinding el cual hace referencia al layout-xml -> listado_item_ordenprod
    // el cual permitirá utilizar los componentes de la vista selleccionada
    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoItemOrdenprodBinding.bind(view)


    }
}