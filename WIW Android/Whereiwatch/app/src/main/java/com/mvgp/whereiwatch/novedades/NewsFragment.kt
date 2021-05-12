package com.mvgp.whereiwatch.novedades

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvgp.whereiwatch.NoticiasActivity
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.pelicula.PeliculaActivity
import com.mvgp.whereiwatch.recyclers.HorizontalRecyclerNewsAdapter
import com.mvgp.whereiwatch.recyclers.VerticalRecyclerSitios


class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       val rootView =  inflater.inflate(R.layout.news_fragment, container, false)

        val progressNewsSites = rootView.findViewById<ProgressBar>(R.id.progressbarNewsSites)
        val progressNewsNoticias = rootView.findViewById<ProgressBar>(R.id.progressbarNewsNoticias)



        //declaracion del recycler
       val recyclerSites = rootView.findViewById<RecyclerView>(R.id.recycler_sites_news)
       val recyclerNews = rootView.findViewById<RecyclerView>(R.id.recycler_noticias)

        //Declaracion del layoutManager
        recyclerSites.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerNews.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)


        //declaracion del adaptador
        val adapterSites = VerticalRecyclerSitios()
        val adapterNews = HorizontalRecyclerNewsAdapter()

        recyclerSites.adapter = adapterSites
        recyclerNews.adapter = adapterNews



        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        viewModel.sitiosList.observe(viewLifecycleOwner, Observer {
            adapterSites.submitList(it)
            progressNewsSites.visibility = View.GONE

        })

        viewModel.noticiasList.observe(viewLifecycleOwner, Observer {
            adapterNews.submitList(it)
            progressNewsNoticias.visibility = View.GONE

        })

        adapterNews.onItemClickListener = {
            val intent = Intent(context, NoticiasActivity::class.java)
            intent.putExtra("Noticia", it)
            startActivity(intent)
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}