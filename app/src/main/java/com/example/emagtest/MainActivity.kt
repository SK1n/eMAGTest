package com.example.emagtest

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.emagtest.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    var tabTitles = arrayOf("Now playing", "Popular","Top Rated", "Upcoming")
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pager2 = binding.moviesPager
        val tabLayout = binding.moviesTabLayout
        pager2.adapter = MainActivityPager(supportFragmentManager, lifecycle)
        TabLayoutMediator(tabLayout, pager2) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

}