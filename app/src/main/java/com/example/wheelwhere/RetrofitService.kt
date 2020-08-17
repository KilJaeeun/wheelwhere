package com.example.wheelwhere

import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

    @GET("/posts/")
    fun getDataList(): Call<ArrayList<Place>>

    @POST("/posts/")
    fun createData(
        @Body rest: Place
    ): Call<Place>

    @GET("/comments/")
    fun getComments(): Call<ArrayList<CommentRegister>>

    @POST("/comments/")
    fun createComment(
        @Body rest: CommentRegister
    ): Call<CommentRegister>

}