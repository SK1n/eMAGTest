package com.example.emagtest.ui.tabNowPlaying

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.emagtest.adapters.MoviesAdapter
import com.example.emagtest.databinding.FragmentTabNowPlayingBinding
import com.example.emagtest.databinding.FragmentTabPopularBinding
import com.example.emagtest.ui.customViews.MarginDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TabNowPlaying : Fragment() {

    private val viewModel: TabNowPlayingViewModel by viewModels()
    private var _binding: FragmentTabNowPlayingBinding? = null
    private lateinit var pagerAdapter: MoviesAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabNowPlayingBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            pagerAdapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.isLoading.value = loadStates.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launch {
            viewModel.getData().collectLatest { pagerAdapter.submitData(it) }
        }
    }

    private fun setupRecyclerView() {
        pagerAdapter = MoviesAdapter()
        binding.nowPlayingRecycler.apply {
            adapter = pagerAdapter
            addItemDecoration(MarginDecoration(context))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}