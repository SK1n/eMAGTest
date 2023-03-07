package com.example.emagtest.ui.tabHome

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.emagtest.ui.moviesNowPlaying.MoviesNowPlaying
import com.example.emagtest.ui.moviesPopular.MoviesPopular
import com.example.emagtest.ui.moviesTopRated.MoviesTopRated
import com.example.emagtest.ui.moviesUpcoming.MoviesUpcoming
import com.example.emagtest.utils.Constants.POSITION_ONE
import com.example.emagtest.utils.Constants.POSITION_THREE
import com.example.emagtest.utils.Constants.POSITION_TWO
import com.example.emagtest.utils.Constants.POSITION_ZERO
import com.example.emagtest.utils.Constants.TOTAL_ADAPTER_POSITIONS

class TabHomePager(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return TOTAL_ADAPTER_POSITIONS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            POSITION_ZERO -> MoviesNowPlaying()
            POSITION_ONE -> MoviesPopular()
            POSITION_TWO -> MoviesTopRated()
            POSITION_THREE -> MoviesUpcoming()
            else -> MoviesNowPlaying()
        }
    }
}