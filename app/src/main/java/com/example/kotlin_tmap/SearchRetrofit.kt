package com.example.kotlin_tmap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchRetrofit {
    private var retrofit = Retrofit.Builder()
        .baseUrl("http://3.35.90.80")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getService():RetrofitService=retrofit.create(RetrofitService::class.java)
}