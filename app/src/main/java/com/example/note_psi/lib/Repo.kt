package com.example.note_psi.lib

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.note_psi.model.Data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

object Repo {
    val user = FirebaseAuth.getInstance().uid.toString()
    private val firebase = FirebaseDatabase.getInstance().reference.child("PSI").child(user)
    fun getdata(user:String,kategori: Boolean): LiveData<MutableList<Data>> {
        val mutableList = MutableLiveData<MutableList<Data>>()
        FirebaseDatabase.getInstance().reference.child("PSI").child(user).orderByChild("kategori").equalTo(kategori)
            .addValueEventListener(object : ValueEventListener {
                val listData = mutableListOf<Data>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    listData.clear()
                    snapshot.children.forEach {
                        val data = it.getValue(Data::class.java) as Data
                        val id = data.id
                        val judul = data.judul
                        val ket = data.keterangan
                        val kat = data.kategori
                        val biodata = Data(id, judul, ket, kat)
                        listData.add(biodata)
                        firebase.push().key
                    }
                    mutableList.value = listData
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        return mutableList
    }

    fun getdataFilter(kategori: Boolean, p0: String): LiveData<MutableList<Data>> {
        val mutableList = MutableLiveData<MutableList<Data>>()
        firebase.orderByChild("kategori").equalTo(kategori)
            .addValueEventListener(object : ValueEventListener {
                val listData = mutableListOf<Data>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    listData.clear()
                    snapshot.children.forEach {
                        val data = it.getValue(Data::class.java) as Data
                        val id = data.id
                        val judul = data.judul
                        val ket = data.keterangan
                        val kat = data.kategori
                        val biodata = Data(id, judul, ket, kat)
                        listData.add(biodata)
                        firebase.push().key
                    }
                    mutableList.value = listData
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        return mutableList
    }

    fun addData(data: Data) {

        firebase.push().setValue(data)

    }

    fun deleteData(id: String) {
        val applesQuery: Query = firebase.orderByChild("id").equalTo(id)
        applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (appleSnapshot in snapshot.getChildren()) {
                    appleSnapshot.ref.removeValue()
                }
            }
        })
    }

    fun editData(data: Data) {
        val applesQuery: Query = firebase.orderByChild("id").equalTo(data.id)
        applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (appleSnapshot in snapshot.children) {
                    appleSnapshot.ref.setValue(data)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun pin(id: String) {
        firebase.orderByChild("id").equalTo(id)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {

                            val data = it.getValue(Data::class.java)
                            if (!data!!.kategori) {
                                FirebaseDatabase.getInstance().reference.child("PSI")
                                    .child(FirebaseAuth.getInstance().uid.toString())
                                    .child(it.key.toString()).child("kategori").setValue(true)
                            } else {
                                FirebaseDatabase.getInstance().reference.child("PSI")
                                    .child(FirebaseAuth.getInstance().uid.toString())
                                    .child(it.key.toString()).child("kategori").setValue(false)
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("MSG", "" + error.message)
                    }
                })
    }

}