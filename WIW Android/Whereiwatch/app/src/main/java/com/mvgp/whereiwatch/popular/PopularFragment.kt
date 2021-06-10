package com.mvgp.whereiwatch.popular

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mvgp.whereiwatch.pelicula.PeliculaActivity
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.recyclers.HorizontalRecyclerAdapter
import com.mvgp.whereiwatch.recyclers.VerticalRecyclerSitios
import com.mvgp.whereiwatch.recyclers.VerticalRecyclerSitiosPop

class PopularFragment : Fragment() {

    companion object {
        fun newInstance() = PopularFragment()
    }

    private lateinit var viewModel: PopularViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.popular_fragment, container, false)
        viewModel = ViewModelProvider(this).get(PopularViewModel::class.java)

        val progressBarSites = rootView.findViewById<ProgressBar>(R.id.ProgressbarPopSites)
        val progressBarPeliculas = rootView.findViewById<ProgressBar>(R.id.progressbarPopPeliculas)


        //declaracion del recycler
        val recycler_sites = rootView.findViewById<RecyclerView>(R.id.recycler_sites_pop)
        val recycler_pops = rootView.findViewById<RecyclerView>(R.id.recycler_peliculas_pop)

        //Declaracion del layoutManager
        recycler_sites.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
        recycler_pops.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)


        //declaracion del adaptador
        val adapterSites = VerticalRecyclerSitios()
        recycler_sites.adapter = adapterSites

        val adapterPeliculasPopulares = HorizontalRecyclerAdapter()
        recycler_pops.adapter = adapterPeliculasPopulares


        adapterSites.onItemClickListener = {
            viewModel.filtraPorSitios(it.id)
        }


        //Declaracion onclick

        adapterPeliculasPopulares.onItemClickListener = {
            val intent = Intent(context, PeliculaActivity::class.java)
            intent.putExtra("Pelicula", it)
            startActivity(intent)
        }



        val searchbar = rootView.findViewById<SearchView>(R.id.pop_searchView)
        searchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (viewModel.buscaPelicula(query)){

                }else{
                    Snackbar.make(rootView,"No se encuentra",Snackbar.LENGTH_LONG).show()
                    return false
                }
                return false

            }
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.buscaPelicula(newText)
                return false
            }
        })



        viewModel.sitiosList.observe(viewLifecycleOwner, Observer {
            progressBarSites.visibility = View.GONE
            adapterSites.submitList(it)
        })

        viewModel.peliculasPopularesList.observe(viewLifecycleOwner, Observer {
            progressBarPeliculas.visibility = View.GONE
            adapterPeliculasPopulares.submitList(it)
        })

        return rootView
    }



}