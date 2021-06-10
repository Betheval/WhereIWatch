package com.mvgp.whereiwatch.list

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mvgp.whereiwatch.pelicula.PeliculaActivity
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.recyclers.HorizontalRecyclerAdapter

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =  inflater.inflate(R.layout.list_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        val searchbar = rootView.findViewById<SearchView>(R.id.list_searchView)
        val radioVisto = rootView.findViewById<Button>(R.id.radioButtonVisto)
        val radioPendiente = rootView.findViewById<Button>(R.id.radioPendiente)

       var isVistoSelected:Boolean = true
        var isPendigSelected:Boolean = false

        radioVisto.setOnClickListener {
            if (isVistoSelected){
                isVistoSelected = false
                isPendigSelected = true
            it.setBackgroundColor(resources.getColor(R.color.primaryColor))
                radioPendiente.setBackgroundColor(resources.getColor(R.color.black))
                viewModel.onListPelisCharger(false)

            }else{
                isVistoSelected = true
                isPendigSelected = false
                it.setBackgroundColor(resources.getColor(R.color.secondaryColor))
                radioPendiente.setBackgroundColor(resources.getColor(R.color.primaryLightColor))
                viewModel.onListPelisCharger(true)

            }
        }
        radioPendiente.setOnClickListener {
            if (isPendigSelected){
                isVistoSelected = true
                isPendigSelected = false
                it.setBackgroundColor(resources.getColor(R.color.primaryLightColor))
                radioVisto.setBackgroundColor(resources.getColor(R.color.black))
                viewModel.onListPelisCharger(true)
            }else{
                isVistoSelected = false
                isPendigSelected = true
                it.setBackgroundColor(resources.getColor(R.color.black))
                radioVisto.setBackgroundColor(resources.getColor(R.color.primaryColor))
                viewModel.onListPelisCharger(false)
            }
        }

        searchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (viewModel.buscaPelicula(query)){

                }else{
                    Snackbar.make(rootView,"No se encuentra", Snackbar.LENGTH_LONG).show()
                    return false
                }
                return false

            }
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.buscaPelicula(newText)
                return false
            }
        })



        val recycler = rootView.findViewById<RecyclerView>(R.id.recycler_mylist)

        recycler.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
        val adapterList = HorizontalRecyclerAdapter()
        recycler.adapter = adapterList


        //Declaracion on click

        adapterList.onItemClickListener = {
            val intent = Intent(context, PeliculaActivity::class.java)
            intent.putExtra("Pelicula", it)
            startActivity(intent)
        }

        viewModel.peliculasList.observe(viewLifecycleOwner, Observer {
            adapterList.submitList(it)
        })


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}