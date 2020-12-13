package com.example.note_psi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.note_psi.R
import com.example.note_psi.lib.OptionMenu
import com.example.note_psi.lib.Repo
import com.example.note_psi.lib.ViewModel
import com.example.note_psi.model.Data
import com.example.note_psi.ui.HomeAdapterPin
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList


class HomeFragment : OptionMenu(), View.OnClickListener {
    var adapterpin: HomeAdapterPin? = null
    //Menginisialisai VIewModel
    private val homeMainView by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }

    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser

        adapter = HomeAdapter(requireContext())
        adapterpin = HomeAdapterPin(requireContext())
        recyclerviewHome.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerviewHomePin.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerviewHome.adapter = adapter
        recyclerviewHomePin.adapter = adapterpin
        observe()
        observePin()
        floatingActionButton.setOnClickListener(this)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(requireContext(), "" + p0, Toast.LENGTH_SHORT).show()

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Toast.makeText(requireContext(), "" + p0, Toast.LENGTH_SHORT).show()
//                homeMainView.getData(false).observe(viewLifecycleOwner, Observer {
////                    it.fi
//                    adapter.setListData(it)
//                    adapter.notifyDataSetChanged()
//                })
                return false
            }

        })
    }

    private fun observePin() {
        val user = FirebaseAuth.getInstance().currentUser?.uid.toString()
        homeMainView.getData(user,true).observe(viewLifecycleOwner, Observer {
            adapterpin?.setListData(it)
            adapterpin?.notifyDataSetChanged()
        })
    }

    private fun observe() {
        val user = FirebaseAuth.getInstance().currentUser?.uid.toString()
        homeMainView.getData(user,false).observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.floatingActionButton -> findNavController().navigate(R.id.action_homeFragment_to_addFragment2)
        }
    }

}