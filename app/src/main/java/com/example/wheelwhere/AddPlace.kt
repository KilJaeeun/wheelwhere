package com.example.wheelwhere

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_place.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable


class AddPlace : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        val retrofit = Retrofit.Builder().baseUrl("http://3.35.90.80")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitService::class.java)

        var restFlag: Boolean = false
        var parkFlag: Boolean = false
        var elvFlag: Boolean = false
        var helpFlag: Boolean = false

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
                    Log.d("result!!", "is_elibator : " + dataList?.get(0)?.is_elibator)
                    Log.d("result!!", "is_parking : " + dataList?.get(0)?.is_parking)
                    Log.d("result!!", "is_helper : " + dataList?.get(0)?.is_helper)
                    Log.d("result!!", "address : " + dataList?.get(0)?.address)
                    Log.d("result!!", "is_tuck : " + dataList?.get(0)?.is_tuck)
                    Log.d("result!!", "description : " + dataList?.get(0)?.description)
                    Log.d("result!!", "latitude : " + dataList?.get(0)?.latitude)
                    Log.d("result!!", "longitude : " + dataList?.get(0)?.longitude)
                    Log.d("result!!", "star : " + dataList?.get(0)?.star)
                    Log.d("result!!", "author : " + dataList?.get(0)?.author)
                }
            }
        })


        isRest_add.setOnClickListener {
            if (restFlag == false) it.setBackgroundColor(Color.rgb(255, 165, 0))
            else it.setBackgroundColor(Color.TRANSPARENT)
            restFlag = !restFlag
        }

        isPark_add.setOnClickListener {
            if (parkFlag == false) it.setBackgroundColor(Color.rgb(255, 165, 0))
            else it.setBackgroundColor(Color.TRANSPARENT)
            parkFlag = !parkFlag
        }

        isEle_add.setOnClickListener {
            if (elvFlag == false) it.setBackgroundColor(Color.rgb(255, 165, 0))
            else it.setBackgroundColor(Color.TRANSPARENT)
            elvFlag = !elvFlag
        }

        isHelper_add.setOnClickListener {
            if (helpFlag == false) it.setBackgroundColor(Color.rgb(255, 165, 0))
            else it.setBackgroundColor(Color.TRANSPARENT)
            helpFlag = !helpFlag
        }

        submit_add.setOnClickListener {
            val edt1 = findViewById<EditText>(R.id.new_name_add) as EditText
            val name: String = edt1.text.toString()
            val edt2 = findViewById<EditText>(R.id.new_address_add) as EditText
            val address: String = edt2.text.toString()
            //val edt3 = findViewById<EditText>(R.id.new_number_add) as EditText
            //val number: String = edt3.text.toString()

            val rest: Place =
                Place(
                    name = name,
                    is_toilet = restFlag,
                    is_parking = parkFlag,
                    is_elibator = elvFlag,
                    is_helper = helpFlag,
                    address = address,
                    is_tuck = false,
                    description = "null",
                    latitude = "38",
                    longitude = "126",
                    star = "1"
                )

            Log.d("result!!", "name : " + rest.name)
            Log.d("result!!", "address : " + rest.address)
            Log.d("result!!", "restFlag : " + rest.is_toilet)
            Log.d("result!!", "parkFlag : " + rest.is_parking)
            Log.d("result!!", "elvFlag : " + rest.is_elibator)
            Log.d("result!!", "helpFlag : " + rest.is_helper)

            service.createData(rest).enqueue(object : Callback<Place> {
                override fun onFailure(call: Call<Place>, t: Throwable) {
                    Log.d("result!!", "failed to upload !")
                }

                override fun onResponse(call: Call<Place>, response: Response<Place>) {
                    if (response.isSuccessful) {
                        val rest = response.body()
                        Log.d("result!!", "uploaded !")
                    }
                }
            })

        }

    }
}