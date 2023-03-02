package com.example.emagtest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.emagtest.ui.tabNowPlaying.TabNowPlaying
import com.example.emagtest.ui.tabPopular.TabPopular
import com.example.emagtest.ui.tabTopRated.TabTopRated
import com.example.emagtest.ui.tabUpcoming.TabUpcoming
import com.example.emagtest.utils.Constants.POSITION_ONE
import com.example.emagtest.utils.Constants.POSITION_THREE
import com.example.emagtest.utils.Constants.POSITION_TWO
import com.example.emagtest.utils.Constants.POSITION_ZERO
import com.example.emagtest.utils.Constants.TOTAL_ADAPTER_POSITIONS

class MainActivityPager(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return TOTAL_ADAPTER_POSITIONS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            POSITION_ZERO -> TabNowPlaying()
            POSITION_ONE -> TabPopular()
            POSITION_TWO -> TabTopRated()
            POSITION_THREE -> TabUpcoming()
            else -> TabNowPlaying()
        }
    }
}