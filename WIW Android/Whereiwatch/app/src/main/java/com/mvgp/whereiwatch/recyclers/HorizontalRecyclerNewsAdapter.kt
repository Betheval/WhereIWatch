package com.mvgp.whereiwatch.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.models.Noticia
import com.mvgp.whereiwatch.models.Pelicula
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pelicula_horizontal.view.*

class HorizontalRecyclerNewsAdapter: ListAdapter<Noticia, HorizontalRecyclerNewsAdapter.ViewHolder>(DiffCallback) {


    companion object DiffCallback: DiffUtil.ItemCallback<Noticia>(){
        override fun areItemsTheSame(oldItem: Noticia, newItem: Noticia): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Noticia, newItem: Noticia): Boolean {
            return oldItem == newItem
        }
    }
    lateinit var onItemClickListener: (Noticia) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalRecyclerNewsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_noticia, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalRecyclerNewsAdapter.ViewHolder, position: Int) {
        val noticia = getItem(position)
        holder.bind(noticia)


    }

    inner class ViewHolder(val view:View): RecyclerView.ViewHolder(view) {
        val titulo = view.findViewById<TextView>(R.id.titulo_noticia)
        val subtitulo = view.findViewById<TextView>(R.id.subtitulo_noticia)
        val portada = view.findViewById<ImageView>(R.id.imagen_noticia)

        fun bind(noticia: Noticia){

            titulo.text = noticia.titulo
            subtitulo.text = noticia.subtitulo
            Picasso.get()
                .load(noticia.imagen)
                .resize(100,120)
                .into(portada)

            view.setOnClickListener{
                if (::onItemClickListener.isInitialized){
                    onItemClickListener(noticia)
                }
            }

        }

    }
}
