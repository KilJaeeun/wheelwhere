package com.example.wheelwhere

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
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_empty_commetn.*
import kotlinx.android.synthetic.main.activity_place_detail.*
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

class PlaceDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)
        getPlaceInfoAndDraw(this@PlaceDetail)
        NetworkTask2(
            comments_list,
            LayoutInflater.from(this@PlaceDetail)
        ).execute()

    }


    fun getPlaceInfoAndDraw(activity: Activity) {
        val post_ids = intent.getStringExtra("post")
        val name = intent.getStringExtra("name")
        val address = intent.getStringExtra("address")
        place_name.setText(name)
        post.setText(post_ids)
        place_address.setText(address)
        write_comment.setOnClickListener {
            val intent = Intent(this@PlaceDetail, AddComment::class.java)
            intent.putExtra("post", post_ids)
            activity.startActivity(intent)
        }
    }
}

class NetworkTask2(
    val recyclerView: RecyclerView,
    val inflater: LayoutInflater
) : AsyncTask<Any?, Any?, Array<CommentRegister>>() {
    override fun onPostExecute(result: Array<CommentRegister>) {
        //다른 실행함수는 서브 앱티브티에서 실행되지ㅏㄴ, 이 친구는 메인쓰레드에서  실행된다.

        val adapter = CommentAdapter(result!!, inflater)
        recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(context)
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: Any?): Array<CommentRegister> {
        val urlString: String = "http://3.35.90.80/comments/"
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
        val data = Gson().fromJson(buffer, Array<CommentRegister>::class.java)
        return data
    }
}

class CommentAdapter(
    val personList: Array<CommentRegister>,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView

        init {
            text = itemView.findViewById(R.id.comment_item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.setText(personList.get(position).text ?: "")
    }
}