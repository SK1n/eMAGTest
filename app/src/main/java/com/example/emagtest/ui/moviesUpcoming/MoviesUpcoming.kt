package com.example.emagtest.ui.moviesUpcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.emagtest.adapters.MoviesAdapter
import com.example.emagtest.adapters.MoviesLoadStateAdapter
import com.example.emagtest.databinding.FragmentMoviesUpcomingBinding
import com.example.emagtest.ui.customViews.MarginDecoration
import com.example.emagtest.ui.tabHome.TabHomeDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesUpcoming : Fragment() {

    private val viewModel: MoviesUpcomingViewModel by viewModels()
    private var _binding: FragmentMoviesUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var pagerAdapter: MoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesUpcomingBinding.inflate(inflater, container, false)
        navController = findNavController()
        pagerAdapter = MoviesAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movieRecycler.apply {
            setHasFixedSize(true)
            addItemDecoration(MarginDecoration(context))
            adapter = pagerAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter { pagerAdapter.retry() },
                footer = MoviesLoadStateAdapter { pagerAdapter.retry() },
            )
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}