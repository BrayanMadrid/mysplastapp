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
import com.example.mysplast.databinding.ListadoItemIngresoBinding
import com.example.mysplast.model.Itemtransaccion

class ItemIngresoAdapter(private val listener: View.OnClickListener):
    ListAdapter<Itemtransaccion, RecyclerView.ViewHolder>(ItemIngresoAdapter.OrdenDiffCallback()) {

    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_item_ingreso,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemIngreso = getItem(position)
        with(holder as ItemIngresoAdapter.ViewHolder){

            binding.edtLineaIngreso.text = itemIngreso.linea.toString()
            binding.edtProductoIngreso.text = itemIngreso.id_producto.nombre.toString()
            binding.edtCantidadIngreso.text = itemIngreso.cantidad.toString()

        }
    }

    class OrdenDiffCallback: DiffUtil.ItemCallback<Itemtransaccion>(){
        override fun areItemsTheSame(oldItem: Itemtransaccion, newItem: Itemtransaccion): Boolean  = oldItem.id_itemtransaccion == newItem.id_itemtransaccion

        override fun areContentsTheSame(oldItem: Itemtransaccion, newItem: Itemtransaccion): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoItemIngresoBinding.bind(view)


    }
}