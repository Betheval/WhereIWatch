package com.mvgp.whereiwatch.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.models.Pelicula
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pelicula_vertical.view.*

class VerticalRecyclerAdapter: ListAdapter<Pelicula, VerticalRecyclerAdapter.VViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<Pelicula>(){
        override fun areItemsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
            return oldItem == newItem
        }
    }

    lateinit var onItemClickListener: (Pelicula) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalRecyclerAdapter.VViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_pelicula_vertical, parent, false)
        return VViewHolder(view)
    }

    override fun onBindViewHolder(holder: VViewHolder, position: Int) {
        val pelicula = getItem(position)
        holder.bind(pelicula)
    }


    inner class VViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val tiulo = view.findViewById<TextView>(R.id.nombre_pelicula_itemVert)
        val portada = view.findViewById<ImageView>(R.id.img_item_pelicula_itemVert)
        val rating = view.findViewById<RatingBar>(R.id.ratingBarVert)

        fun bind(pelicula: Pelicula) {
            tiulo.text = pelicula.nombre
            Picasso.get()
                .load(pelicula.portada)
                .resize(100,120)
                .into(portada)
            rating.rating = pelicula.ratingPoints/100.toFloat()
            view.setOnClickListener{
                if (::onItemClickListener.isInitialized){
                    onItemClickListener(pelicula)
                }
            }

        }


    }
}