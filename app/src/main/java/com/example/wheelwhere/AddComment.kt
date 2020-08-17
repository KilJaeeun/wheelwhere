package com.example.wheelwhere

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_empty_commetn.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddComment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_commetn)
        getCommentAndDrawServer(this@AddComment)
    }

    fun getCommentAndDrawServer(activity:Activity) {
        val post_id = intent.getStringExtra("post_id")
        recieve_comment_btn.setOnClickListener {
            val retrofit = Retrofit.Builder().baseUrl("http://3.35.90.80")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(RetrofitService::class.java)

            val comment: CommentRegister =
                CommentRegister(
                    text = recieve_comment.toString(),
                    post_id = post_id.toInt()
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
                        Toast.makeText(activity, "후기작성에 성공하였습니다. ", Toast.LENGTH_LONG).show()
                        val rest = response.body()
                        Log.d("result!!", "uploaded !")
                    }
                }
            })
        }


    }
}