package com.example.kotlin_tmap

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_comment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddComment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)
        var flag = 0
        getCommentAndDrawServer(this@AddComment, 0)
    }
    fun getCommentAndDrawServer(activity: Activity, flag_input: Int) {
        var flag = flag_input
        go_back.setOnClickListener {
            super.onBackPressed()
        }

//        // get reference to ImageView
        var star_input = findViewById(R.id.star_input) as ImageView
//// set on-click listener for ImageView

        star_input.setOnClickListener {
            Log.d("star_debug", ""+flag)

                when (flag) {
                    0 -> {
                        flag += 1
                        star_input.setImageResource(R.drawable.star1)
                    }
                    1 -> {
                        flag += 1
                        star_input.setImageResource(R.drawable.star2)
                    }
                    2 -> {
                        flag += 1
                        star_input.setImageResource(R.drawable.star3)
                    }
                    3 -> {
                        flag += 1
                        star_input.setImageResource(R.drawable.star4)
                    }
                    4 -> {
                        flag += 1
                        star_input.setImageResource(R.drawable.star5)
                    }
                    else -> {
                        flag = 0
                        star_input.setImageResource(R.drawable.star0)
                    }


                }

        }

        val post = intent.getStringExtra("post")
        recieve_comment_btn.setOnClickListener {
            val retrofit = Retrofit.Builder().baseUrl("http://3.35.90.80")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(RetrofitService::class.java)

            val comment: CommentRegister =
                CommentRegister(
                    text = recieve_comment.text.toString(),
                    post = post.toInt()
                )

            service.createComment(comment).enqueue(object : Callback<CommentRegister> {
                override fun onFailure(call: Call<CommentRegister>, t: Throwable) {
                    Log.d("result!!", "failed to upload !")
                    Toast.makeText(activity, "후기작성에 실패하였습니다. ", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<CommentRegister>,
                    response: Response<CommentRegister>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(activity, "후기작성에 성공하였습니다. " + post, Toast.LENGTH_LONG).show()
                        val rest = response.body()
                        Log.d("result!!", "uploaded !")
                        //뒤로가기
                        onBackPressed()
                    }
                }
            })
        }


    }
}