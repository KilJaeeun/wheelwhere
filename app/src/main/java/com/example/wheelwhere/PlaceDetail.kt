package com.example.wheelwhere

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_empty_commetn.*
import kotlinx.android.synthetic.main.activity_place_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)
        getPlaceInfoAndDraw(this@PlaceDetail)


    }


    fun getPlaceInfoAndDraw(activity:Activity) {
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


        val retrofit = Retrofit.Builder().baseUrl("http://3.35.90.80")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitService::class.java)


//    service.getComments().enqueue(object : Callback<ArrayList<Place>> {
//        override fun onFailure(call: Call<ArrayList<Place>>, t: Throwable) {
//            Log.d("result!!", "Error (getting data)!")
//        }
//
//        override fun onResponse(
//            call: Call<ArrayList<Place>>,
//            response: Response<ArrayList<Place>>
//        ) {
//            if (response.isSuccessful) {
//                val dataList = response.body()
//                Log.d("result!!", "id : " + dataList?.get(0)?.id)
//                Log.d("result!!", "name : " + dataList?.get(0)?.name)
//                Log.d("result!!", "is_toilet : " + dataList?.get(0)?.is_toilet)
//                Log.d("result!!", "is_elibator : " + dataList?.get(0)?.is_elibator)
//                Log.d("result!!", "is_parking : " + dataList?.get(0)?.is_parking)
//                Log.d("result!!", "is_helper : " + dataList?.get(0)?.is_helper)
//                Log.d("result!!", "address : " + dataList?.get(0)?.address)
//                Log.d("result!!", "is_tuck : " + dataList?.get(0)?.is_tuck)
//                Log.d("result!!", "description : " + dataList?.get(0)?.description)
//                Log.d("result!!", "latitude : " + dataList?.get(0)?.latitude)
//                Log.d("result!!", "longitude : " + dataList?.get(0)?.longitude)
//                Log.d("result!!", "star : " + dataList?.get(0)?.star)
//                Log.d("result!!", "author : " + dataList?.get(0)?.author)
//            }
//        }
//    })
}
