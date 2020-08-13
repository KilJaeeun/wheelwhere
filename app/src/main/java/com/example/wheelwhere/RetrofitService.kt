package com.example.wheelwhere

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

    @GET("/posts/")
    fun getDataList(): Call<ArrayList<TestInfo>>

    @POST("/posts/")
    fun createData(
        @Body rest: TestInfo
    ): Call<TestInfo>

}