package com.example.emagtest.ui.tabFavorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.emagtest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabFavorites : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_favorites, container, false)
    }
}