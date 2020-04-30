package com.example.kotlin_tmap.ui.home.adapter.holder

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_tmap.R
import com.example.kotlin_tmap.data.MapPoint
import kotlinx.android.synthetic.main.item_point_list.view.*

class PointViewHolder(context: Context,parent : ViewGroup?)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_point_list,parent,false)){

    fun onBind(item : MapPoint){
        itemView.onBind(item)
    }

    private fun View.onBind(item : MapPoint){
        //받아온 데이터 Bind
        tvName.text=item.name
        tvLat.text=item.latitude.toString()
        tvLon.text=item.longitude.toString()
    }

}