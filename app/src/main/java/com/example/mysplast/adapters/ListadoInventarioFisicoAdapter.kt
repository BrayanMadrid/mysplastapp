package com.example.mysplast.adapters

import android.content.Context
import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysplast.R
import com.example.mysplast.databinding.ListadoInventariofisicoBinding
import com.example.mysplast.model.InventarioFisico
import java.text.SimpleDateFormat

class ListadoInventarioFisicoAdapter(private val listener: View.OnClickListener):
    ListAdapter<InventarioFisico, RecyclerView.ViewHolder>(ListadoInventarioFisicoAdapter.ProductoDiffCallback()), View.OnClickListener  {

    private lateinit var context: Context
    private lateinit var itemInventarioFisicoAdapter: ItemInventarioFisicoAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.listado_inventariofisico,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obtenerInventarioFisico = getItem(position)

        with(holder as ListadoInventarioFisicoAdapter.ViewHolder){

            itemInventarioFisicoAdapter = ItemInventarioFisicoAdapter(this)
            binding.rcvDetalleReceta.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = itemInventarioFisicoAdapter
            }

            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(obtenerInventarioFisico.fecha)
            binding.edtNroInventarioFisico.text = obtenerInventarioFisico.nroinventario
            binding.edtResponsableInventarioFisico.text = obtenerInventarioFisico.responsable.nombres +" "+ obtenerInventarioFisico.responsable.ape_pat +" "+ obtenerInventarioFisico.responsable.ape_mat
            binding.edtSectorInventarioFisico.text = obtenerInventarioFisico.id_sector.nom_sector
            binding.edtAlmacenInventarioFisico.text = obtenerInventarioFisico.id_sector.id_almacen.nom_almacen
            binding.edtFechaInventarioFisico.text = currentDate

            if (obtenerInventarioFisico.estado=="P"){
                binding.edtEstadoInventarioFisico.text = "Pendiente"
            } else if (obtenerInventarioFisico.estado=="N"){
                binding.edtEstadoInventarioFisico.text = "Anulado"
            } else{
                binding.edtEstadoInventarioFisico.text = "Regularizado"
            }
            itemInventarioFisicoAdapter.submitList(obtenerInventarioFisico.items)

            val cardView: CardView = binding.cardviewinventariofisico
            val contenidoOculto: LinearLayout = binding.layoutOcultoInventarioFisico
            val btnVisualizar: Button = binding.btnVisualizarInventarioFisico
            btnVisualizar.setOnClickListener {
                if(contenidoOculto.visibility == View.VISIBLE){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                        TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                    }
                    contenidoOculto.visibility = View.GONE
                    btnVisualizar.text = "VISUALIZAR"
                } else {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                        TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                    }
                    contenidoOculto.visibility = View.VISIBLE
                    btnVisualizar.text = "OCULTAR"
                }
            }

        }
    }

    class ProductoDiffCallback: DiffUtil.ItemCallback<InventarioFisico>(){
        override fun areItemsTheSame(oldItem: InventarioFisico, newItem: InventarioFisico): Boolean  = oldItem.id_invent == newItem.id_invent

        override fun areContentsTheSame(oldItem: InventarioFisico, newItem: InventarioFisico): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view),
        View.OnClickListener {

        val binding = ListadoInventariofisicoBinding.bind(view)
        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }


    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}