package com.mvgp.whereiwatch.recyclers

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.models.Noticia
import com.mvgp.whereiwatch.models.Pelicula
import com.mvgp.whereiwatch.models.Sitio
import com.squareup.picasso.Picasso

class VerticalRecyclerSitios: ListAdapter<Sitio, VerticalRecyclerSitios.ViewHolder>(DiffCallback){


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val logo = view.findViewById<ImageView>(R.id.logo_site)

    }

    companion object DiffCallback: DiffUtil.ItemCallback<Sitio>(){
        override fun areItemsTheSame(oldItem: Sitio, newItem: Sitio): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Sitio, newItem: Sitio): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sitios, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sitio = getItem(position)
        Picasso.get()
                .load(sitio.logo)
                .resize(30,30)
                .into(holder.logo)
    }
}