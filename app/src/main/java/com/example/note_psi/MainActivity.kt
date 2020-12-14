package com.example.note_psi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import com.example.note_psi.lib.SharedPref
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myPreference = SharedPref(this)
        if (myPreference.getData()== 1){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
//            findNavController(nav_host_fragment.id).navigate(R.id.action_loginFragment_to_homeFragment)

        } else {
            findNavController(nav_host_fragment.id).navigate(R.id.action_homeFragment_to_loginFragment)

        }
    }

}