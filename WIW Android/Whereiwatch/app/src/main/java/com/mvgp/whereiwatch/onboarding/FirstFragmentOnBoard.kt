package com.mvgp.whereiwatch.onboarding

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mvgp.whereiwatch.MainActivity
import com.mvgp.whereiwatch.R


class FirstFragmentOnBoard : Fragment() {

    /*
    TODO: Botón para cambiar de fragmento
     */
    var passOnboard: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val rootView : View = inflater.inflate(R.layout.fragment_first, container, false)


        val nextbutton = rootView.findViewById<Button>(R.id.buttonNext)
        nextbutton.setOnClickListener {
            it.findNavController().navigate(R.id.action_firstFragmentOnBoard_to_secondFragmentOnBoard)
        }
        return rootView
    }


}

