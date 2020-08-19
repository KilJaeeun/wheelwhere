package com.example.kotlin_tmap.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat
import com.example.kotlin_tmap.ui.home.HomeFragment
import java.util.jar.Manifest

class permission {


    private val tag = "퍼미션 메세지"
    private val userLocationAgreeCode = 1
    private val homeFragment = HomeFragment()

    //필요한 퍼미션 리스트
    //원하는 퍼미션을 이곳에 추가하면 된다.
    private val requiredPermissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )



    companion object{
        //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        var rejectedPermissionList = ArrayList<String>()
    }



    fun checkPermission(context: Context){

        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(context as Activity, permission) != PackageManager.PERMISSION_GRANTED){
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }

        }

        //거절된 퍼미션이 있다면...
        if (rejectedPermissionList.isNotEmpty()) {
            //권한 요청!
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(context as Activity, rejectedPermissionList.toArray(array), userLocationAgreeCode)
        }

    }

    fun checkStatus(context: Context) : Boolean{
        var result = false
        for (permission in requiredPermissions) {
            result = ContextCompat.checkSelfPermission(context as Activity, permission) == PackageManager.PERMISSION_GRANTED
            Log.d("퍼미션 체크 스탯","$result")
        }
        return result
    }

    fun handlePermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            userLocationAgreeCode -> {
                if( grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    Log.i(tag,"Agree location Permission $permissions")

                } else{
                    Log.i(tag,"Not agree location Permission")
                }

            }
        }
    }
}