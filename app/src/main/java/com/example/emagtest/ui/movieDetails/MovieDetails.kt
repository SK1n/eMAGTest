package com.example.emagtest.ui.movieDetails

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.emagtest.R
import com.example.emagtest.adapters.MoviesTagAdapter
import com.example.emagtest.bindImage
import com.example.emagtest.databinding.FragmentMovieDetailsBinding
import com.example.emagtest.models.MoviesDetailsModel
import com.example.emagtest.ui.customViews.MarginDecoration
import com.example.emagtest.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetails : Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModels()
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var moviesTagAdapter: MoviesTagAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        navController = findNavController()

        binding.backArrow.setOnClickListener {
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
            navController.popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movieData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { response ->
                        bind(response)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.d(ContentValues.TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun bind(data: MoviesDetailsModel) {
        val movieBackdrop = resources.getString(R.string.base_poster_path, data.backdrop_path)
        val moviePoster = resources.getString(R.string.base_poster_path, data.poster_path)
        bindImage(binding.backdropImage, movieBackdrop)
        bindImage(binding.posterImage, moviePoster)

        binding.apply {
            movieTitle.text = data.title
            movieRating.text = resources.getString(
                R.string.rating_format,
                data.vote_average,
            )
            movieRatingCount.text = resources.getString(
                R.string.rating_count_format, data.vote_count.toString()
            )
            movieRelease.text = resources.getString(R.string.release_in, data.release_date)
            movieTagline.text = data.tagline
            movieOverview.text = data.overview
        }

        moviesTagAdapter = MoviesTagAdapter(data.genres)

        binding.genresRecycler.apply {
            adapter = moviesTagAdapter
            addItemDecoration(MarginDecoration(context))
        }
    }

}