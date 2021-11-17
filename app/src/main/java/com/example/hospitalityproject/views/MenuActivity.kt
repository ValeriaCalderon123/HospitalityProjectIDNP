package com.example.hospitalityproject.views

import android.app.Notification
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hospitalityproject.R
import com.example.hospitalityproject.databinding.ActivityMenuBinding
import com.example.hospitalityproject.views.ui.dashboard.DashboardFragment
import com.example.hospitalityproject.views.ui.hospitals.HospitalsFragment
import com.example.hospitalityproject.views.ui.notifications.NotificationsFragment
import com.example.hospitalityproject.views.ui.profile.ProfileFragment


class MenuActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu)

        bottomNavigationView = findViewById(R.id.nav_view)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home-> replaceFragment(DashboardFragment())
                R.id.navigation_dashboard -> replaceFragment(HospitalsFragment())
                R.id.navigation_analytics -> replaceFragment(NotificationsFragment())
                R.id.navigation_profile -> replaceFragment(ProfileFragment())
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_menu, fragment)
        transaction.commit()
    }

    fun replaceFragment2(fragment: Fragment, id: String){
        val bundle = Bundle()
        bundle.putString("id", id)
        fragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_menu, fragment)
        transaction.commit()
    }
}