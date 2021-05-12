package com.mvgp.whereiwatch.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.login.LoginActivity


class SecondFragmentOnBoard : Fragment() {



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_second, container, false)

        val buttonNext = view.findViewById<Button>(R.id.buttonNextSecond)
        buttonNext.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)

        }

        return view
    }

}