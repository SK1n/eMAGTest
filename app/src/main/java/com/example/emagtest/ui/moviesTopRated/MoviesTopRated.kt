package com.example.emagtest.ui.moviesTopRated

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
import com.example.emagtest.R
import com.example.emagtest.adapters.MoviesAdapter
import com.example.emagtest.databinding.FragmentMoviesTopRatedBinding
import com.example.emagtest.ui.customViews.MarginDecoration
import com.example.emagtest.ui.tabHome.TabHomeDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesTopRated : Fragment() {

    private val viewModel: MoviesTopRatedViewModel by viewModels()
    private var _binding: FragmentMoviesTopRatedBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var pagerAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesTopRatedBinding.inflate(inflater, container, false)
        navController = findNavController()
        pagerAdapter = MoviesAdapter(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        pagerAdapter.onItemClick = {
            val directions = TabHomeDirections.actionTabHomeToNavigationMovieDetails(it.id!!)
            findNavController().navigate(directions)
        }
        viewModel.movies.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                pagerAdapter.submitData(it)
            }
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