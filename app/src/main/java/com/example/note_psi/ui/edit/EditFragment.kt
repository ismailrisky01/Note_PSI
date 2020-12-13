package com.example.note_psi.ui.edit

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.note_psi.R
import com.example.note_psi.lib.ViewModel
import com.example.note_psi.model.Data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.delete_form.view.*
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : Fragment(), View.OnClickListener {
    private val MainView by lazy {
        ViewModelProvider(this).get(ViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    var id: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments.apply {
            id = this?.getString("id").toString()
            val judul = this?.getString("judul").toString()
            val keterangan = this?.getString("keterangan").toString()
            val kategori = this?.getString("kategori").toString()
            edtEditJudul.setText(judul)
            edtEditKeterngan.setText(keterangan)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_edit, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.pin -> {
                pin()
                true
            }
            R.id.delete -> {
                delete()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun pin() {
        FirebaseDatabase.getInstance().reference.child("PSI")
            .child(FirebaseAuth.getInstance().uid.toString()).orderByChild("id").equalTo(id)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {

                            val data = it.getValue(Data::class.java)
                            if (!data!!.kategori){
                                FirebaseDatabase.getInstance().reference.child("PSI").child(FirebaseAuth.getInstance().uid.toString()).child(it.key.toString()).child("kategori").setValue(true)
                            }else{
                                FirebaseDatabase.getInstance().reference.child("PSI").child(FirebaseAuth.getInstance().uid.toString()).child(it.key.toString()).child("kategori").setValue(false)
                            }
                            Toast.makeText(requireContext(), "" + it.key, Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("MSG",""+error.message)
                    }
                })
    }

    fun edit() {
        val judul = edtEditJudul.text.toString()
        val keterangan = edtEditKeterngan.text.toString()
        val id = arguments?.getString("id").toString()
        MainView.editdata(Data(id, judul, keterangan, false))
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {


        }
    }

    private fun delete() {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.delete_form, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
//            .setTitle("Edit Form")
        //show dialog
        val mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.btnDeleteYes.setOnClickListener {
            //dismiss dialog

            MainView.deleteData(id)
            mAlertDialog.dismiss()
            //get text from EditTexts of custom layout

        }
        //cancel button click of custom layout
        mDialogView.btnDeleteNo.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }

    }

}