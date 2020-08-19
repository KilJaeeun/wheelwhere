package com.example.kotlin_tmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kotlin_tmap.ui.dashboard.DashboardFragment
import com.example.kotlin_tmap.ui.home.HomeFragment
import com.example.kotlin_tmap.ui.notifications.NotificationsFragment
import com.example.kotlin_tmap.util.permission
import com.example.kotlin_tmap.util.replace

class MainActivity : AppCompatActivity() {

    private val homeFragment : HomeFragment by lazy {
        HomeFragment()
    }

    private val dashboardFragment: DashboardFragment by lazy{
        DashboardFragment()
    }

    private val notificationFragment : NotificationsFragment by lazy{
        NotificationsFragment()
    }

    //퍼미션 사용
    private lateinit var permission : permission

    val fragmentManger = supportFragmentManager


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item->
        when(item.itemId){
            R.id.navigation_home->{
                fragmentManger.beginTransaction().replace(R.id.container,homeFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard->{
                fragmentManger.beginTransaction().replace(R.id.container,dashboardFragment).commit()
                /*replace(R.id.container, dashboardFragment)*/
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications->{
                fragmentManger.beginTransaction().replace(R.id.container,notificationFragment).commit()
                /*replace(R.id.container, notificationFragment)*/
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainCheckPermission()

        replace(R.id.container, homeFragment)


        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun mainCheckPermission(){
        permission = permission()
        permission.checkPermission(this)
        Log.d("생명주기 ", "메인에서 먼저 체크")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permission.handlePermissionsResult(requestCode,permissions, grantResults)
        Log.d("테스트 ", "HandlerPermissionResult에 보냄")
    }



}