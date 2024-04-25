package com.example.powerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_nav)

        bottomNavigationView.setOnItemSelectedListener{menuItem ->
            when(menuItem.itemId){
                R.id.Bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.menu -> {
                    replaceFragment(MenuFragment())
                    true
                }

                R.id.inbox -> {
                    replaceFragment(InboxFragment())
                    true
                }

                R.id.promotions -> {
                    replaceFragment(PromotionsFragment())
                    true
                }

                R.id.power -> {
                    replaceFragment(PowerFragment())
                    true
                }
                else -> false
            }
        }
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment (fragment: Fragment){
supportFragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit()
    }
}