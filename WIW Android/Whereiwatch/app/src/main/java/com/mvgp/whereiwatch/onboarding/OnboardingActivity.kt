package com.mvgp.whereiwatch.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mvgp.whereiwatch.R
import com.mvgp.whereiwatch.login.LoginActivity
import com.mvgp.whereiwatch.login.PREFSKEY

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        val isPassOnboard = getValuePreference(this)
        if (isPassOnboard){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }

    fun getValuePreference(context: Context): Boolean {
        val preferences: SharedPreferences = context.getSharedPreferences(PREFSKEY, MODE_PRIVATE)
        return preferences.getBoolean("isPassOnboard", false)
    }
}