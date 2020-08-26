package com.example.kotlin_tmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_tmap.map.MapExample
import kotlinx.android.synthetic.main.activity_add_comment.*
import kotlinx.android.synthetic.main.activity_map_example.*
import kotlinx.android.synthetic.main.activity_my_page_t.*
<<<<<<< Updated upstream
import kotlinx.android.synthetic.main.activity_my_page_t.go_back
import kotlinx.android.synthetic.main.activity_place_detail.*

=======
>>>>>>> Stashed changes
class MyPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_t)
        add_place.setOnClickListener{
            val intent = Intent(this, AddPlace::class.java)
            startActivity(intent)
            finish()
        }
        go_back.setOnClickListener {
            super.onBackPressed()
        }
        my_post.setOnClickListener {
            val intent = Intent(this, PlaceList::class.java)
            startActivity(intent)
            finish()
        }

        // start of navibar
        btn_map_of_my.setOnClickListener {
            startActivity(Intent(this, MapExample::class.java))
            finish()
        }

        btn_list_of_my.setOnClickListener{
            startActivity(Intent(this, PlaceList::class.java))
            finish()
        }

        btn_my_of_my.setOnClickListener{
            startActivity(Intent(this, MyPage::class.java))
            finish()
        }
        // end of navi-bar

    }
}