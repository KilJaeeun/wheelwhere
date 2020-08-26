package com.example.kotlin_tmap


import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_place_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PlaceList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)

        val retrofit = Retrofit.Builder().baseUrl("http://3.35.90.80")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitService::class.java)

        /*NetworkTask(
            recycler_places,
            LayoutInflater.from(this@PlaceList),
            this@PlaceList
        ).execute()*/
        service.getDataList().enqueue(object : Callback<ArrayList<Place>> {
            override fun onFailure(call: Call<ArrayList<Place>>, t: Throwable) {
                Log.d("result!!", "Error (getting data)!")
            }

            override fun onResponse(
                call: Call<ArrayList<Place>>,
                response: Response<ArrayList<Place>>
            ) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    var places = ArrayList<Place>()
                    for (d in dataList!!) {
                        //Log.d("result!!", "name : " + d.name)
                        places.add(d)
                    }

                    val adapter = RecyclerViewAdapter(places, LayoutInflater.from(this@PlaceList), this@PlaceList)
                    recycler_places.adapter = adapter
                    recycler_places.layoutManager = LinearLayoutManager(this@PlaceList)
                }
            }
        })
    }
}

class RecyclerViewAdapter(
    val itemList: ArrayList<Place>,
    val inflater: LayoutInflater,
    val activity: Activity
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pname: TextView
        val paddress: TextView
        val pnumber: TextView

        init {
            pname = itemView.findViewById(R.id.list_name_card)
            paddress = itemView.findViewById(R.id.list_address_card)
            pnumber = itemView.findViewById(R.id.list_number_card)

            itemView.setOnClickListener {
                val intent = Intent(activity, PlaceDetail::class.java)
                intent.putExtra("name", itemList[adapterPosition].name)
                intent.putExtra("address", itemList[adapterPosition].address)
                intent.putExtra("number", itemList[adapterPosition].phone)
                activity.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.location_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pname.setText(itemList[position].name)
        holder.paddress.setText(itemList[position].address)
        holder.pnumber.setText(itemList[position].phone)
    }
}
/*
class NetworkTask(
    val recyclerView: RecyclerView,
    val inflater: LayoutInflater,
    val activity: Activity
) : AsyncTask<Any?, Any?, Array<LocationRegister>>() {
    override fun onPostExecute(result: Array<LocationRegister>) {
        //다른 실행함수는 서브 앱티브티에서 실행되지ㅏㄴ, 이 친구는 메인쓰레드에서  실행된다.

        val adapter = PersonAdapter(result!!, inflater, activity)
        recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(context)
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: Any?): Array<LocationRegister> {
        val urlString: String = "http://3.35.90.80/posts/"
        val url: URL = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json") //헤더
        var buffer = ""
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log.d("connn", "inputstream: " + connection.inputStream)
            val reader = BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    "UTF-8"
                )
            )
            buffer = reader.readLine()
        }
        val data = Gson().fromJson(buffer, Array<LocationRegister>::class.java)
        return data
    }
}

class PersonAdapter(
    val personList: Array<LocationRegister>,
    val inflater: LayoutInflater,
    val activity: Activity
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView
        val address: TextView
        val number: TextView

        init {
            number = itemView.findViewById(R.id.list_number_card)
            name = itemView.findViewById(R.id.list_name_card)
            address = itemView.findViewById(R.id.list_address_card)


            itemView.setOnClickListener {
                Log.d("detail","click")
                val intent = Intent(activity, PlaceDetail::class.java)
                intent.putExtra("post", personList.get(adapterPosition).id.toString())
                intent.putExtra("name", personList.get(adapterPosition).name)
                intent.putExtra("address", personList.get(adapterPosition).address)
                activity.startActivity(intent)// activity 가 아니여서 startactitvtiy  를 쓸 수 없다.
//하지만 인자에 이미 activity 가 있기 떄문에 여기서 불러올 수 있다.

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.location_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(personList.get(position).name ?: "")
        holder.address.setText(personList.get(position).address.toString() ?: "")
        holder.number.setText("11111")
    }
}*/