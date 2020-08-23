package com.example.kotlin_tmap


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_place_list.*
import android.os.AsyncTask
import com.google.gson.Gson
import org.w3c.dom.Text
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

        var placeRecyclerAdapter: PlaceRecylerAdapter
/*
        NetworkTask(
            recycler_places,
            LayoutInflater.from(this@PlaceList),
            this@PlaceList
        ).execute()
*/
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
                    Log.d("result!!", "id : " + dataList?.get(0)?.id)
                    Log.d("result!!", "name : " + dataList?.get(0)?.name)
                    Log.d("result!!", "is_toilet : " + dataList?.get(0)?.is_toilet)
                    Log.d("result!!", "is_elevator : " + dataList?.get(0)?.is_elevator)
                    Log.d("result!!", "is_parking : " + dataList?.get(0)?.is_parking)
                    Log.d("result!!", "is_helper : " + dataList?.get(0)?.is_helper)
                    Log.d("result!!", "address : " + dataList?.get(0)?.address)
                    Log.d("result!!", "is_tuck : " + dataList?.get(0)?.is_tuck)
                    Log.d("result!!", "description : " + dataList?.get(0)?.description)
                    Log.d("result!!", "latitude : " + dataList?.get(0)?.latitude)
                    Log.d("result!!", "longitude : " + dataList?.get(0)?.longitude)
                    Log.d("result!!", "star : " + dataList?.get(0)?.star)
                    Log.d("result!!", "author : " + dataList?.get(0)?.author)
                    Log.d("result!!", "len : " + dataList?.size)
                    var temp_list = Places()
                    for (d in dataList!!) {
                        temp_list.addPlace(d)
                    }
                    placeRecyclerAdapter = PlaceRecylerAdapter(
                        temp_list,
                        LayoutInflater.from(this@PlaceList)
                    )
                    Log.d("result!!", "len : " + temp_list.place_list.size)

                    place_list.adaper = placeRecyclerAdapter
                }
            }
        })



    }
}

class PlaceRecylerAdapter(
    val placeList: Places,
    val inflater: LayoutInflater,
) : RecyclerView.Adapter<PlaceRecylerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listName: TextView
        val listAddress: TextView
        val listNumber: TextView

        init {
            listName = itemView.findViewById(R.id.list_name)
            listAddress = itemView.findViewById(R.id.list_address)
            listNumber = itemView.findViewById(R.id.list_number)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.location_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return placeList.place_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.listName.setText(placeList.place_list[position].name)
        holder.listAddress.setText(placeList.place_list[position].address)
        holder.listNumber.setText(placeList.place_list[position].phone)
    }
}

class Places() {
    val place_list = ArrayList<Place>()

    fun addPlace(p: Place) {
        place_list.add(p)
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
        val location_id: TextView

        init {
            location_id = itemView.findViewById(R.id.location_id)
            name = itemView.findViewById(R.id.location_name)
            address = itemView.findViewById(R.id.location_address)


            itemView.setOnClickListener {
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
        val view = inflater.inflate(R.layout.place_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(personList.get(position).name ?: "")
        holder.address.setText(personList.get(position).address.toString() ?: "")
    }
}
*/