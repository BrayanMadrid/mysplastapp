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
import com.example.mysplast.databinding.ListadoItemEgresoBinding
import com.example.mysplast.model.Itemtransaccion

class ItemEgresoAdapter(private val listener: View.OnClickListener):
    ListAdapter<Itemtransaccion, RecyclerView.ViewHolder>(ItemEgresoAdapter.OrdenDiffCallback()) {

    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_item_egreso,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemEgreso = getItem(position)
        with(holder as ItemEgresoAdapter.ViewHolder){

            binding.edtLineaEgreso.text = itemEgreso.linea.toString()
            binding.edtProductoEgreso.text = itemEgreso.id_producto.nombre.toString()
            binding.edtCantidadEgreso.text = itemEgreso.cantidad.toString()

        }
    }

    class OrdenDiffCallback: DiffUtil.ItemCallback<Itemtransaccion>(){
        override fun areItemsTheSame(oldItem: Itemtransaccion, newItem: Itemtransaccion): Boolean  = oldItem.id_itemtransaccion == newItem.id_itemtransaccion

        override fun areContentsTheSame(oldItem: Itemtransaccion, newItem: Itemtransaccion): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoItemEgresoBinding.bind(view)


    }
}