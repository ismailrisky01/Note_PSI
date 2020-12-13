package com.example.note_psi.lib

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.note_psi.lib.Repo
import com.example.note_psi.model.Data
import com.google.android.gms.tasks.OnCompleteListener

class ViewModel: ViewModel() {
    private val repo = Repo
    fun getData(user:String,kategori:Boolean): LiveData<MutableList<Data>> {

        val mutableData = MutableLiveData<MutableList<Data>>()
        repo.getdata(user,kategori).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun addData(data: Data){
        repo.addData(data)
    }
    fun editdata(data: Data){
        repo.editData(data)
    }
    fun deleteData(id:String){
        repo.deleteData(id)
    }
    fun pin(id: String){
        repo.pin(id)
    }

}