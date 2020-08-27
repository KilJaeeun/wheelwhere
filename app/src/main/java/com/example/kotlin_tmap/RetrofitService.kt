package com.example.kotlin_tmap

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

    @FormUrlEncoded
    @POST("/rest-auth/registration/")
    fun register(
        @Field("username") username:String,
        @Field("password1") password1:String,
        @Field("password2") password2:String
    ): Call<User>

    @GET("/search/")
    fun search(
        @Query("word") keyword:String
    ):Call<ArrayList<LocationSearch>>



}