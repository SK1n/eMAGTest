package com.example.emagtest.ui.tabHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.emagtest.databinding.FragmentTabHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class TabHome : Fragment() {

    private var _binding: FragmentTabHomeBinding? = null
    private var tabTitles = arrayOf("Now playing", "Popular", "Top Rated", "Upcoming")
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabHomeBinding.inflate(inflater, container, false)
        val pager2 = binding.moviesPager
        val tabLayout = binding.moviesTabLayout
        pager2.adapter = TabHomePager(childFragmentManager, lifecycle)
        TabLayoutMediator(tabLayout, pager2) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
        return binding.root
    }

}