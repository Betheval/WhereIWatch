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


class HorizontalRecyclerAdapter: ListAdapter<Pelicula, HorizontalRecyclerAdapter.PViewHolder>(DiffCallback) {

    companion object DiffCallback:DiffUtil.ItemCallback<Pelicula>(){
        override fun areItemsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
            return oldItem == newItem
        }
    }

    lateinit var onItemClickListener: (Pelicula) -> Unit


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pelicula_horizontal, parent, false)
        return PViewHolder(view)
    }

    override fun onBindViewHolder(holder: PViewHolder, position: Int) {
        val pelicula = getItem(position)
        holder.bind(pelicula)

    }

    inner class PViewHolder(val view: View):RecyclerView.ViewHolder(view){
        val tiulo = view.findViewById<TextView>(R.id.nombre_item_pelicula)
        val portada = view.findViewById<ImageView>(R.id.img_item_pelicula)
        val rating = view.findViewById<RatingBar>(R.id.ratingBarHorizontal)

        fun bind (pelicula: Pelicula){
            tiulo.text = pelicula.nombre
            Picasso.get()
                .load(pelicula.portada)
                .resize(100,120)
                .into(portada);
            rating.rating = (pelicula.ratingPoints/100).toFloat()
            view.setOnClickListener{
                if (::onItemClickListener.isInitialized){
                    onItemClickListener(pelicula)
                }
            }

        }

    }



}