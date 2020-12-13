package com.example.note_psi.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.note_psi.R
import com.example.note_psi.lib.ViewModel
import com.example.note_psi.model.Data
import kotlinx.android.synthetic.main.fragment_add.*
import java.util.*


class AddFragment : Fragment(), View.OnClickListener {
    private val viewModel by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAdd.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnAdd ->addData()
        }
    }

    fun addData() {
        val id = UUID.randomUUID().toString()
        val judul = edtJudul.text.toString()
        val keterangan = edtKeterngan.text.toString()
        viewModel.addData(Data(id,judul,keterangan,false))
        findNavController().navigate(R.id.action_addFragment_to_homeFragment)
    }

}