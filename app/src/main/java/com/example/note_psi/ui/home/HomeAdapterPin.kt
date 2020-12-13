package com.example.note_psi.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.note_psi.R
import com.example.note_psi.model.Data
import kotlinx.android.synthetic.main.item.view.*
import java.util.*


class HomeAdapterPin(context: Context) : RecyclerView.Adapter<HomeAdapterPin.ViewHolder>() {
    val listdata = arrayListOf("1", "2", "3")
    private var dataList = mutableListOf<Data>()
    fun setListData(settingData: MutableList<Data>) {
        dataList = settingData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val addItem = layoutInflater.inflate(R.layout.item, parent, false)
        return ViewHolder(
            addItem
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.itemView.txtJudul.text = data.judul
        holder.data(data)

        val r = Random()

        val red: Int = r.nextInt(255 - 0 + 1) + 0
        val green: Int = r.nextInt(255 - 0 + 1) + 0
        val blue: Int = r.nextInt(255 - 0 + 1) + 0

        val draw = GradientDrawable()
        draw.shape = GradientDrawable.RECTANGLE
        draw.setColor(Color.rgb(red, green, blue))
        holder.itemView.ID_Card.background = draw
    }

    override fun getItemCount(): Int {
        return dataList.size


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var id:String
        lateinit var judul:String
        lateinit var keternagan:String

        init {
            view.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString("id", id)
                bundle.putString("judul", judul)
                bundle.putString("keterangan", keternagan)

                it.findNavController().navigate(R.id.action_homeFragment_to_editFragment, bundle)
            }
        }
        fun data(data: Data){
            id = data.id
            judul = data.judul
            keternagan = data.keterangan
        }
    }

}