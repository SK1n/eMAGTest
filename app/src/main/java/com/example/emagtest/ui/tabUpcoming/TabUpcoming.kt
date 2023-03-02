package com.example.emagtest.ui.tabUpcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.emagtest.databinding.FragmentTabPopularBinding
import com.example.emagtest.databinding.FragmentTabUpcomingBinding

class TabUpcoming : Fragment() {

    private var _binding: FragmentTabUpcomingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}