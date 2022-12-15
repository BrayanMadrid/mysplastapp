package com.example.mysplast.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mysplast.R
import com.example.mysplast.databinding.ListadoRecetasBinding
import com.example.mysplast.model.Recetaprod
import java.text.SimpleDateFormat
import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager


class ListadoRecetasAdapter (private val listener: View.OnClickListener):
    ListAdapter<Recetaprod, RecyclerView.ViewHolder>(ListadoRecetasAdapter.ProductoDiffCallback()), View.OnClickListener  {

    private lateinit var context: Context
    private lateinit var itemrecetaAdapter: ItemRecetaAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.listado_recetas,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obtenerReceta = getItem(position)

        with(holder as ListadoRecetasAdapter.ViewHolder){

            itemrecetaAdapter = ItemRecetaAdapter(this)
            binding.rcvDetalleReceta.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = itemrecetaAdapter
            }

            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(obtenerReceta.fecha)
            binding.edtNroRecetaProd.text = obtenerReceta.nro_receta
            binding.edtNomReceta.text = obtenerReceta.nom_receta
            binding.edtCategoriaProductoReceta.text = obtenerReceta.id_producto.id_categoria.nom_categoria
            binding.edtUnMedidaReceta.text = obtenerReceta.id_producto.id_unmedida.nom_unmedida
            binding.edtProductoReceta.text = obtenerReceta.id_producto.nombre
            binding.edtCodigoProductoReceta.text = obtenerReceta.id_producto.codexterno
            binding.edtFechaReceta.text = currentDate

            if (obtenerReceta.estado=="A"){
                binding.edtEstadoReceta.text = "Activo"
            } else {
                binding.edtEstadoReceta.text = "Inactivo"
            }
            itemrecetaAdapter.submitList(obtenerReceta.items)

            val cardView: CardView = binding.cardviewrecetas
            val contenidoOculto: LinearLayout = binding.layoutOcultoRecetas
            val btnVisualizar: Button = binding.btnVisualizarReceta
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


    class ProductoDiffCallback: DiffUtil.ItemCallback<Recetaprod>(){
        override fun areItemsTheSame(oldItem: Recetaprod, newItem: Recetaprod): Boolean  = oldItem.id_receta == newItem.id_receta

        override fun areContentsTheSame(oldItem: Recetaprod, newItem: Recetaprod): Boolean  = oldItem == newItem

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view),
        View.OnClickListener {

        val binding = ListadoRecetasBinding.bind(view)
        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }


    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}