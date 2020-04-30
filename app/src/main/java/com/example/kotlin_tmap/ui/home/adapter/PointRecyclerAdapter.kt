package com.example.kotlin_tmap.ui.home.adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_tmap.data.MapPoint
import com.example.kotlin_tmap.ui.home.adapter.holder.PointViewHolder
import com.example.kotlin_tmap.ui.home.adapter.model.PointRecyclerModel
import kotlinx.android.synthetic.main.item_point_list.view.*

class PointRecyclerAdapter (private val context: Context) : PointRecyclerModel
            , RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val list = arrayListOf<MapPoint>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PointViewHolder(context,parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? PointViewHolder)?.onBind(list[position]) //list 위치 찾아 데이터 바인드

        Log.d("테스트 ", "어댑터 내부 리스트 크기 : $itemCount")
    }


    override fun addItem(pointData: MapPoint) {
        list.add(pointData)
    }

    override fun refreshList() {
        list.clear()
    }

    override fun notifychange() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =list.size




}