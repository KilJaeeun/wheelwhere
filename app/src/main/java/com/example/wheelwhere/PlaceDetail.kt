package com.example.wheelwhere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_place_detail.*

class PlaceDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)
        getPlaceInfoAndDraw()
    }


    fun getPlaceInfoAndDraw() {
        val post_ids = intent.getStringExtra("post_id")
        val name = intent.getStringExtra("name")
        val address = intent.getStringExtra("address")
        place_name.setText(name)
        post_id.setText(post_ids)
        place_address.setText(address)
    }
}
