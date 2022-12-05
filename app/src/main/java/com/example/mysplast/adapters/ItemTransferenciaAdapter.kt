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
import com.example.mysplast.databinding.ListadoItemTransferenciaBinding
import com.example.mysplast.model.Itemtransaccion

class ItemTransferenciaAdapter(private val listener: View.OnClickListener):
    ListAdapter<Itemtransaccion, RecyclerView.ViewHolder>(ItemTransferenciaAdapter.OrdenDiffCallback()) {

    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.listado_item_transferencia,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemTransferencia = getItem(position)
        with(holder as ItemTransferenciaAdapter.ViewHolder){

            binding.edtLineaTransferencia.text = itemTransferencia.linea.toString()
            binding.edtProductoTransferencia.text = itemTransferencia.id_producto.nombre.toString()
            binding.edtCantidadTransferencia.text = itemTransferencia.cantidad.toString()

        }
    }

    class OrdenDiffCallback: DiffUtil.ItemCallback<Itemtransaccion>(){
        override fun areItemsTheSame(oldItem: Itemtransaccion, newItem: Itemtransaccion): Boolean  = oldItem.id_itemtransaccion == newItem.id_itemtransaccion

        override fun areContentsTheSame(oldItem: Itemtransaccion, newItem: Itemtransaccion): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){

        val binding = ListadoItemTransferenciaBinding.bind(view)


    }
}