package com.example.emagtest.ui.tabNowPlaying

import android.os.Bundle
import android.util.Log
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
import java.io.Console

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
                if(loadStates.refresh is LoadState.Error ) {
                    Log.d("PagerValue", pagerAdapter.itemCount.toString())

                    viewModel.hasError.value = pagerAdapter.itemCount < 1
                    binding.executePendingBindings()
                    Log.d("PagerValue", viewModel.hasError.value.toString())
                }
            }
        }
        lifecycleScope.launch {
            viewModel.getData().collectLatest {
                pagerAdapter.submitData(it) }

        }
        binding.retry.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getData().collectLatest { pagerAdapter.submitData(it) }
            }
        }
    }

    private fun setupRecyclerView() {
        pagerAdapter = MoviesAdapter()
        binding.movieRecycler.apply {
            adapter = pagerAdapter
            addItemDecoration(MarginDecoration(context))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}