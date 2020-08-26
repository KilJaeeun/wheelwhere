package com.example.kotlin_tmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_add_place.*
import kotlinx.android.synthetic.main.activity_add_place.submit_add
import kotlinx.android.synthetic.main.activity_my_page_f.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

class Activity_Login : AppCompatActivity() {
    var login: Login? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_f)
        var retrofit = Retrofit.Builder()
            .baseUrl("http://3.35.90.80")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var loginService = retrofit.create(LoginService::class.java)
        submit_add.setOnClickListener {
            var id = input_id.text.toString()
            var pwd = input_pwd.text.toString()

            loginService.requestLogin(id, pwd).enqueue(object: Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    Log.e("Login", t.message)
                    var dialog = AlertDialog.Builder(this@Activity_Login)
                    dialog.setTitle("에러")
                    dialog.setMessage("호출실패")
                    dialog.show()
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    login = response.body()
                    Log.d("LOGIN", "msg : " + login?.msg)
                    Log.d("LOGIN", "code : " + login?.code)
                    var dialog = AlertDialog.Builder(this@Activity_Login)
                    dialog.setTitle(login?.msg)
                    dialog.setMessage(login?.code)
                    dialog.show()
                }

            })
        }
    }
}