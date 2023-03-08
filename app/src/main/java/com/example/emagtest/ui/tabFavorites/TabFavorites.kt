package com.example.emagtest.ui.tabFavorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.emagtest.R
import com.example.emagtest.adapters.MoviesAdapter
import com.example.emagtest.databinding.FragmentTabFavoritesBinding
import com.example.emagtest.ui.customViews.MarginDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TabFavorites : Fragment() {

    private val viewModel: TabFavoritesViewModel by viewModels()
    private var _binding: FragmentTabFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var pagerAdapter: MoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabFavoritesBinding.inflate(inflater, container, false)
        navController = findNavController()
        pagerAdapter = MoviesAdapter(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovies()
        setupAdapter()
        showLoadingError()
        retryClickListener()
    }

    private fun setupAdapter() {
        binding.movieRecycler.apply {
            setHasFixedSize(true)
            addItemDecoration(MarginDecoration(context))
            adapter = pagerAdapter
        }
        viewModel.movies.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                pagerAdapter.submitData(PagingData.from(it))
            }
            if(it.isEmpty()) {
                binding.itemNoData.root.visibility = View.VISIBLE
            } else {
                binding.itemNoData.root.visibility = View.GONE
            }
        }
        pagerAdapter.onItemClick = {
            val directions = TabFavoritesDirections.actionTabFavoritesToNavigationMovieDetails(it.id!!)
            findNavController().navigate(directions)
        }
    }

    private fun showLoadingError() = lifecycleScope.launch {
        pagerAdapter.loadStateFlow.collectLatest {
            if (it.refresh is LoadState.Loading) {
                binding.itemLoading.root.visibility = View.VISIBLE
            } else {
                binding.itemLoading.root.visibility = View.GONE
            }
            if (it.refresh is LoadState.Error) {
                binding.itemError.root.visibility = View.VISIBLE
            } else {
                binding.itemError.root.visibility = View.GONE
            }
        }
    }

    private fun retryClickListener() {
        binding.itemError.root.findViewById<Button>(R.id.button_retry).setOnClickListener {
            pagerAdapter.retry()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}