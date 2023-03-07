package com.example.emagtest.ui.tabSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.emagtest.adapters.MoviesAdapter
import com.example.emagtest.adapters.MoviesLoadStateAdapter
import com.example.emagtest.databinding.FragmentTabSearchBinding
import com.example.emagtest.ui.customViews.MarginDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMovies : Fragment() {

    private val viewModel: SearchMoviesViewModel by viewModels()
    private var _binding: FragmentTabSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var pagerAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabSearchBinding.inflate(inflater, container, false)
        navController = findNavController()
        pagerAdapter = MoviesAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchResults(query!!)

                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
        binding.movieRecycler.apply {
            setHasFixedSize(true)
            addItemDecoration(MarginDecoration(context))
            adapter = pagerAdapter.withLoadStateHeaderAndFooter(
                header = MoviesLoadStateAdapter { pagerAdapter.retry() },
                footer = MoviesLoadStateAdapter { pagerAdapter.retry() },
            )
        }
        pagerAdapter.onItemClick = {
            val directions =
                SearchMoviesDirections.actionSearchMoviesToNavigationMovieDetails(it.id!!)
            findNavController().navigate(directions)
        }
        viewModel.movies.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                pagerAdapter.submitData(it)
            }
        }
    }
}