package com.example.emagtest.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.emagtest.R
import com.example.emagtest.bindFavorites
import com.example.emagtest.databinding.ItemMovieBinding
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.room.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class MoviesAdapter:
    PagingDataAdapter<MoviesModel, MoviesAdapter.MovieItemViewHolder>(
        differCallback
    ) {
    var onItemClick: ((MoviesModel) -> Unit)? = null
    var favoritesButton: ((MoviesModel) -> Unit)? = null

    inner class MovieItemViewHolder(private var binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MoviesModel?) {
            binding.movieItem = movie
            binding.itemFavorites.setOnClickListener{favoritesButton?.invoke(movie!!)}
            binding.executePendingBindings()
        }
    }


    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<MoviesModel>() {
            override fun areItemsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        )

    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener { onItemClick?.invoke(getItem(position)!!) }
    }
}