package com.example.kotlin_tmap


import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_empty_commetn.*
import kotlinx.android.synthetic.main.activity_place_detail.*
import kotlinx.android.synthetic.main.activity_place_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class PlaceDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        back_of_detail.setOnClickListener {
            onBackPressed()
        }

        val name = intent.getStringExtra("name")
        val address = intent.getStringExtra("address")
        val number = intent.getStringExtra("number")

        detail_name.setText(name)
        detail_address.setText(address)
        detail_number.setText(number)

        val retrofit = Retrofit.Builder().baseUrl("http://3.35.90.80")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitService::class.java)

        service.getComments().enqueue(object : Callback<ArrayList<CommentRegister>> {
            override fun onFailure(call: Call<ArrayList<CommentRegister>>, t: Throwable) {
                Log.d("result!!", "getting comment error")
            }

            override fun onResponse(
                call: Call<ArrayList<CommentRegister>>,
                response: Response<ArrayList<CommentRegister>>
            ) {
                val dataList = response.body()
                val comments = ArrayList<CommentRegister>()
                for (d in dataList!!) {
                    comments.add(d)
                }

                val adapter = RecyclerViewAdapter2(
                    comments,
                    LayoutInflater.from(this@PlaceDetail),
                    this@PlaceDetail
                )
                recycler_comments.adapter = adapter
                recycler_comments.layoutManager = LinearLayoutManager(this@PlaceDetail)
            }
        })
    }
    /*getPlaceInfoAndDraw(this@PlaceDetail)
    NetworkTask2(
        comments_list,
        LayoutInflater.from(this@PlaceDetail)
    ).execute()
    */
}

class RecyclerViewAdapter2(
    val itemList: ArrayList<CommentRegister>,
    val inflater: LayoutInflater,
    val activity: Activity
) : RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cname: TextView
        val pdate: TextView
        val comment: TextView

        init {
            cname = itemView.findViewById(R.id.username_of_comment)
            pdate = itemView.findViewById(R.id.date_of_comment)
            comment = itemView.findViewById(R.id.comment_of_card)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.comment_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cname.setText(itemList[position].name)
        holder.pdate.setText(itemList[position].date)
        holder.comment.setText(itemList[position].text)
    }
}

/*
    fun getPlaceInfoAndDraw(activity: Activity) {
        place_share.setOnClickListener {
            Toast.makeText(activity, "공유버튼클릭 ", Toast.LENGTH_LONG).show()
            Log.d("share","click")
            try {
                kakaoLink()
                Log.d("share","sucess")

            }catch (e: Exception){
                Log.d("share","share")
            }


        }
        val post_ids = intent.getStringExtra("post")
        val name = intent.getStringExtra("name")
        val address = intent.getStringExtra("address")
        place_name.setText(name)
        post.setText(post_ids)
        place_address.setText(address)
        write_comment.setOnClickListener {
            val intent = Intent(this@PlaceDetail, AddComment::class.java)
            intent.putExtra("post", post_ids)
            activity.startActivity(intent)
        }
    }

    fun kakaoLink() {
        val params = FeedTemplate
            .newBuilder(
                ContentObject.newBuilder(
                    "디저트 사진",
                    "http://mud-kage.kakao.co.kr/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg",
                    LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com").build()
                )
                    .setDescrption("아메리카노, 빵, 케익")
                    .build()
            )
            .setSocial(
                SocialObject.newBuilder().setLikeCount(10).setCommentCount(20)
                    .setSharedCount(30).setViewCount(40).build()
            )
            .addButton(
                ButtonObject(
                    "웹에서 보기",
                    LinkObject.newBuilder().setWebUrl("https://developers.kakao.com").setMobileWebUrl(
                        "https://developers.kakao.com"
                    ).build()
                )
            )
            .addButton(
                ButtonObject(
                    "앱에서 보기", LinkObject.newBuilder()
                        .setWebUrl("'https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()
                )
            )
            .build()

        val serverCallbackArgs: MutableMap<String, String> =
            HashMap()
        serverCallbackArgs["user_id"] = "\${current_user_id}"
        serverCallbackArgs["product_id"] = "\${shared_product_id}"

        KakaoLinkService.getInstance().sendDefault(
            this,
            params,
            serverCallbackArgs,
            object : ResponseCallback<KakaoLinkResponse?>() {
                override fun onFailure(errorResult: ErrorResult) {
                    //Logger.e(errorResult.toString())
                }

                override fun onSuccess(result: KakaoLinkResponse?) { // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                }
            })
    }
}

class NetworkTask2(
    val recyclerView: RecyclerView,
    val inflater: LayoutInflater
) : AsyncTask<Any?, Any?, Array<CommentRegister>>() {
    override fun onPostExecute(result: Array<CommentRegister>) {
        //다른 실행함수는 서브 앱티브티에서 실행되지ㅏㄴ, 이 친구는 메인쓰레드에서  실행된다.

        val adapter = CommentAdapter(result!!, inflater)
        recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(context)
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: Any?): Array<CommentRegister> {
        val urlString: String = "http://3.35.90.80/comments/"
        val url: URL = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json") //헤더
        var buffer = ""
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log.d("connn", "inputstream: " + connection.inputStream)
            val reader = BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    "UTF-8"
                )
            )
            buffer = reader.readLine()
        }
        val data = Gson().fromJson(buffer, Array<CommentRegister>::class.java)
        return data
    }
}

class CommentAdapter(
    val personList: Array<CommentRegister>,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView

        init {
            text = itemView.findViewById(R.id.comment_item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.setText(personList.get(position).text ?: "")
    }
}*/
