package com.mvgp.whereiwatch.inicio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvgp.whereiwatch.pelicula.PeliculaActivity
import com.mvgp.whereiwatch.recyclers.HorizontalRecyclerAdapter
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.recyclers.VerticalRecyclerAdapter


class InicioFragment : Fragment() {



    companion object {
        fun newInstance() = InicioFragment()
    }


    private lateinit var viewModel: InicioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.inicio_fragment, container, false)
        val topprogresbar = rootView.findViewById<ProgressBar>(R.id.topProgressbar)
        val bottomprogresbar = rootView.findViewById<ProgressBar>(R.id.bottomProgressbar)

        //Declararion de recycler
        val recycler_more = rootView.findViewById<RecyclerView>(R.id.recycler_more_recom)
        val recycler_rec = rootView.findViewById<RecyclerView>(R.id.inicio_recycler_horizontal_mov)


        //Declaracion del layoutManager
        recycler_more.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        recycler_rec.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)


        // declaracion del adapter

        val adapterV = VerticalRecyclerAdapter()
        val adapterH = HorizontalRecyclerAdapter()
        recycler_more.adapter= adapterH
        recycler_rec.adapter = adapterV

        adapterH.onItemClickListener = {
            val intent = Intent(context, PeliculaActivity::class.java)
            intent.putExtra("Pelicula", it)
            startActivity(intent)
        }
        adapterV.onItemClickListener = {
            val intent = Intent(context, PeliculaActivity::class.java)
            intent.putExtra("Pelicula", it)
            startActivity(intent)
        }


        viewModel = ViewModelProvider(this).get(InicioViewModel::class.java)
        viewModel.peliculasRecommendedExtra.observe(viewLifecycleOwner, Observer {
            topprogresbar.visibility = View.GONE
            adapterH.submitList(it)

        })
        viewModel.peliculasRecommend.observe(viewLifecycleOwner, Observer {
            bottomprogresbar.visibility = View.GONE
            adapterV.submitList(it)
        })

        return rootView
    }




}