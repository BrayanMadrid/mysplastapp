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
import com.example.mysplast.databinding.ListadoItemOrdencompraBinding
import com.example.mysplast.model.ItemOrdencompra

class ItemOrdenCompraAdapter(private val listener: View.OnClickListener):
    ListAdapter<ItemOrdencompra, RecyclerView.ViewHolder>(ItemOrdenCompraAdapter.OrdenDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_item_ordencompra,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemOrdencompra = getItem(position)
        with(holder as ItemOrdenCompraAdapter.ViewHolder){

            binding.edtProductoOrdenCompra.text = itemOrdencompra.id_producto.nombre.toString()
            binding.edtCantidadOrdenCompra.text = itemOrdencompra.cantidad.toString()
            binding.edtPrecioOrdenCompra.text = itemOrdencompra.precio.toString()
            binding.edtTotalOrdenCompra.text = itemOrdencompra.total.toString()

        }
    }

    class OrdenDiffCallback: DiffUtil.ItemCallback<ItemOrdencompra>(){
        override fun areItemsTheSame(oldItem: ItemOrdencompra, newItem: ItemOrdencompra): Boolean  = oldItem.id_itemordencompra == newItem.id_itemordencompra

        override fun areContentsTheSame(oldItem: ItemOrdencompra, newItem: ItemOrdencompra): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoItemOrdencompraBinding.bind(view)


    }
}