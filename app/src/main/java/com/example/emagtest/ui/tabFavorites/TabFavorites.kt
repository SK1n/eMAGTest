package com.example.emagtest.ui.tabFavorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.emagtest.R
import com.example.emagtest.adapters.MoviesAdapter
import com.example.emagtest.adapters.MoviesLoadStateAdapter
import com.example.emagtest.databinding.FragmentMoviesUpcomingBinding
import com.example.emagtest.databinding.FragmentTabFavoritesBinding
import com.example.emagtest.ui.customViews.MarginDecoration
import com.example.emagtest.ui.moviesUpcoming.MoviesUpcomingViewModel
import com.example.emagtest.ui.tabHome.TabHomeDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
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
        binding.movieRecycler.apply {
            setHasFixedSize(true)
            addItemDecoration(MarginDecoration(context))
            adapter = pagerAdapter
            pagerAdapter.onItemClick = {
                val directions = TabFavoritesDirections.actionTabFavoritesToNavigationMovieDetails(it.id!!)
                findNavController().navigate(directions)
            }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}