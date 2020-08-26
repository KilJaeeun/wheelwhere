package com.example.kotlin_tmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Activity_Sign_up : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        var retrofit = Retrofit.Builder()
            .baseUrl("http://3.35.90.80")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var signupServive = retrofit.create(RetrofitService::class.java)
        btn_signup.setOnClickListener {
            val id = input_id.text.toString()
            val pwd1 = input_pwd1.text.toString()
            val pwd2 = input_pwd2.text.toString()
            signupServive.register(id, pwd1, pwd2).enqueue(object:
                Callback<User>{
                override fun onFailure(call: Call<User>, t: Throwable) {
                    var dialog = AlertDialog.Builder(this@Activity_Sign_up)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패")
                    dialog.show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    var dialog = AlertDialog.Builder(this@Activity_Sign_up)
                    dialog.setTitle("성공")
                    dialog.setMessage("성공")
                    dialog.show()
                }
            })
        }
    }
}