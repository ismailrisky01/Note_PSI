package com.example.note_psi.lib

import android.content.Context

class SharedPref(context: Context) {
    val DATA_TEMA = "TEMA"
    val KEY_DATA_TEMA = "TEMA_DATA"
    val preference = context.getSharedPreferences(DATA_TEMA,Context.MODE_PRIVATE)


    fun getData():Int?{
        return preference.getInt(KEY_DATA_TEMA,0)
    }
    fun setData(dataInput:Int){
        val data= preference.edit()
        data.putInt(KEY_DATA_TEMA, dataInput)
        data.apply()
    }
}