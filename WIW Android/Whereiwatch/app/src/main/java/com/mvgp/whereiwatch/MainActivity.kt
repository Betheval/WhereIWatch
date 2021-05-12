package com.mvgp.whereiwatch


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.mvgp.whereiwatch.inicio.InicioFragment
import com.mvgp.whereiwatch.list.ListFragment
import com.mvgp.whereiwatch.models.Pelicula
import com.mvgp.whereiwatch.novedades.NewsFragment
import com.mvgp.whereiwatch.pelicula.PeliculaViewModel
import com.mvgp.whereiwatch.popular.PopularFragment
import kotlinx.android.synthetic.main.activity_login.*


class MainActivity : AppCompatActivity() {

    val PREFSKEY = "mispreferencias";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences: SharedPreferences = application.getSharedPreferences(
            PREFSKEY,
            AppCompatActivity.MODE_PRIVATE
        )
        val uid = preferences.getString("uid", null)
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "Inicio"

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val mOnNavigationItemSelectedListener = BottomNavigationView
            .OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_inicio -> {
                        val fragment = InicioFragment.newInstance()
                        openFragment(fragment)
                        toolbar.title = "Inicio"
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_popular -> {
                        val fragment = PopularFragment.newInstance()
                        openFragment(fragment)
                        toolbar.title = "Popular"

                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_novedades -> {
                        val fragment = NewsFragment.newInstance()
                        toolbar.title = "Novedades"
                        openFragment(fragment)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_milista -> {
                        val fragment = ListFragment.newInstance()
                        val bundle: Bundle = Bundle()
                        bundle.putString("uid", uid)
                        fragment.arguments = bundle
                        toolbar.title = "Mi lista"
                        openFragment(fragment)
                        return@OnNavigationItemSelectedListener true
                    }

                }
                false


            }

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_container, fragment)
//        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_right_corner, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cerrar_sesion_button -> {
                finish()
                true
            }
            R.id.salir_button -> {
                finishAffinity()
                System.exit(0)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun saveValuePreference(context: Context, uid: String) {
        val settings: SharedPreferences =
            context.getSharedPreferences(com.mvgp.whereiwatch.login.PREFSKEY, MODE_PRIVATE)
        val editor: SharedPreferences.Editor
        editor = settings.edit()
        editor.putString("uid", uid)
        editor.apply()
    }

}



