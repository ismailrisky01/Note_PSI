package com.example.note_psi.model

class Data(val id:String="",val judul:String,val keterangan:String,val kategori:Boolean) {
    constructor():this("","","",true)
}
