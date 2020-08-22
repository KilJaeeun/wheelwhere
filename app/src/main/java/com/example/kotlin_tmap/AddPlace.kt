package com.example.kotlin_tmap

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

        back_of_add.setOnClickListener{
            onBackPressed()
        }

        isRest_add.setOnClickListener {
            if (restFlag == false) it.isSelected = true
            else it.isSelected = false
            restFlag = !restFlag
        }

        isPark_add.setOnClickListener {
            if (parkFlag == false) it.isSelected = true
            else it.isSelected = false
            parkFlag = !parkFlag
        }

        isEle_add.setOnClickListener {
            if (elvFlag == false) it.isSelected = true
            else it.isSelected = false
            elvFlag = !elvFlag
        }

        isHelper_add.setOnClickListener {
            if (helpFlag == false) it.isSelected = true
            else it.isSelected = false
            helpFlag = !helpFlag
        }

        submit_add.setOnClickListener {
            val edt1 = findViewById<EditText>(R.id.new_name_add) as EditText
            val name: String = edt1.text.toString()
            val edt2 = findViewById<EditText>(R.id.new_address_add) as EditText
            val address: String = edt2.text.toString()
            val edt3 = findViewById<EditText>(R.id.new_number_add) as EditText
            val phone: String = edt3.text.toString()

            val rest: Place =
                Place(
                    name = name,
                    phone = phone,
                    is_helper = helpFlag,
                    is_parking = parkFlag,
                    is_tuck = false,
                    is_elevator = elvFlag,
                    is_toilet = restFlag,
                    description = "null",
                    latitude = "38",
                    longitude = "126",
                    address = address,
                    star = "1"
                )

            Log.d("result!!", "name : " + rest.name)
            Log.d("result!!", "address : " + rest.address)
            Log.d("result!!", "restFlag : " + rest.is_toilet)
            Log.d("result!!", "parkFlag : " + rest.is_parking)
            Log.d("result!!", "elvFlag : " + rest.is_elevator)
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
