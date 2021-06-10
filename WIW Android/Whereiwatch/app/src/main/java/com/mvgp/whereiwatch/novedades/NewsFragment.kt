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


        val progressNewsNoticias = rootView.findViewById<ProgressBar>(R.id.progressbarNewsNoticias)



        //declaracion del recycler
       val recyclerNews = rootView.findViewById<RecyclerView>(R.id.recycler_noticias)

        //Declaracion del layoutManager

        recyclerNews.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)


        //declaracion del adaptador

        val adapterNews = HorizontalRecyclerNewsAdapter()


        recyclerNews.adapter = adapterNews



        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)


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


}