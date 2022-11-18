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
import com.example.mysplast.databinding.ActivityViewOrdenPactivityBinding
import com.example.mysplast.databinding.ListadoItemOrdenprodBinding
import com.example.mysplast.databinding.ListadoOrdenesprodBinding
import com.example.mysplast.model.ItemOrdenprod

class ItemOrdenProdAdapter(private val listener: View.OnClickListener):
    ListAdapter<ItemOrdenprod, RecyclerView.ViewHolder>(ItemOrdenProdAdapter.OrdenDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_item_ordenprod,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemOrdenprod = getItem(position)
        with(holder as ViewHolder){

            binding.edtLinea.text = itemOrdenprod.line.toString()
            binding.edtProductoOrdenProd.text = itemOrdenprod.id_producto.nombre.toString()
            binding.edtCantidadOrdenProd.text = itemOrdenprod.cantidad.toString()

        }
    }


    class OrdenDiffCallback: DiffUtil.ItemCallback<ItemOrdenprod>(){
        override fun areItemsTheSame(oldItem: ItemOrdenprod, newItem: ItemOrdenprod): Boolean  = oldItem.id_itemordenprod == newItem.id_itemordenprod

        override fun areContentsTheSame(oldItem: ItemOrdenprod, newItem: ItemOrdenprod): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoItemOrdenprodBinding.bind(view)


    }
}