package com.example.emagtest.ui.tabTopRated

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
import com.example.emagtest.databinding.FragmentTabTopRatedBinding
import com.example.emagtest.ui.customViews.MarginDecoration
import com.example.emagtest.ui.tabPopular.TabPopularViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TabTopRated : Fragment() {

    private val viewModel: TabTopRatedViewModel by viewModels()
    private lateinit var pagerAdapter: MoviesAdapter
    private var _binding: FragmentTabTopRatedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabTopRatedBinding.inflate(inflater, container, false)
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