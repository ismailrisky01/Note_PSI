package com.example.note_psi.lib

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.note_psi.R

open class OptionMenu : Fragment() {
    private var progressBar: ProgressBar? = null

    fun setProgressBar(bar: ProgressBar) {
        progressBar = bar
    }

    fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }



    public override fun onStop() {
        super.onStop()
        hideProgressBar()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.darkmode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                val myPreference = SharedPref(requireContext())
                myPreference.setData(0)
                true
            }
            R.id.lightmode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                val myPreference = SharedPref(requireContext())
                myPreference.setData(1)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}