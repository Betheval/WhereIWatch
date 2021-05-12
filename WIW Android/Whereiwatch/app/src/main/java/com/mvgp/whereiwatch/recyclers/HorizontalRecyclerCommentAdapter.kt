package com.mvgp.whereiwatch.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.models.Comentario
import com.mvgp.whereiwatch.models.Pelicula

class HorizontalRecyclerCommentAdapter: ListAdapter<Comentario, HorizontalRecyclerCommentAdapter.ViewHolder>(DiffCallback) {


    companion object DiffCallback: DiffUtil.ItemCallback<Comentario>(){
        override fun areItemsTheSame(oldItem: Comentario, newItem: Comentario): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comentario, newItem: Comentario): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comentario = getItem(position)
        holder.bind(comentario)
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val comentario = view.findViewById<TextView>(R.id.comment_data)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBarComment)
        val comentarista = view.findViewById<TextView>(R.id.textUserComent)

        fun bind (coment: Comentario){
            comentario.text = coment.comentario
            ratingBar.numStars = coment.puntuacion.toInt()
            comentarista.text = coment.comentarista

        }

    }
}