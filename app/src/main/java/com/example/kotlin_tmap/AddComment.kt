package com.example.kotlin_tmap


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_comment.*
import kotlinx.android.synthetic.main.activity_empty_commetn.*
import kotlinx.android.synthetic.main.activity_empty_commetn.recieve_comment
import kotlinx.android.synthetic.main.activity_empty_commetn.recieve_comment_btn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddComment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_comment)
        getCommentAndDrawServer(this@AddComment)
    }

    fun getCommentAndDrawServer(activity:Activity) {
        go_back.setOnClickListener {
            super.onBackPressed()
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
                        Toast.makeText(activity, "후기작성에 성공하였습니다. "+post, Toast.LENGTH_LONG).show()
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