package com.example.emagtest.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.emagtest.R
import com.example.emagtest.databinding.ItemMovieBinding
import com.example.emagtest.di.LocalRepositoryEntryPoint
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.room.LocalRepository
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MoviesAdapter(context: Context) :
    PagingDataAdapter<MoviesModel, MoviesAdapter.MovieItemViewHolder>(
        differCallback
    ) {
    var onItemClick: ((MoviesModel) -> Unit)? = null
    var favoritesClickListener: ((MoviesModel) -> Unit)? = null

    var localRepository: LocalRepository

    init {
        val entryPoint =
            EntryPointAccessors.fromApplication(context, LocalRepositoryEntryPoint::class.java)
        localRepository = entryPoint.getLocalRepository()
    }

    inner class MovieItemViewHolder(private var binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MoviesModel?) {
            binding.movieItem = movie
            GlobalScope.launch {
                if (localRepository.elementExists(movie!!.id!!)) {
                    binding.itemFavorites.setImageResource(R.drawable.baseline_favorite_black_18)
                } else {
                    binding.itemFavorites.setImageResource(R.drawable.baseline_favorite_border_black_18)
                }
            }
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
        holder.itemView.findViewById<ImageView>(R.id.item_favorites).setOnClickListener {
            setFavoriteClickListener(position)
        }
    }

    private fun setFavoriteClickListener(position: Int) = GlobalScope.launch {
        val movie = getItem(position)
        if (localRepository.getMovies().contains(movie)) {
            localRepository.delete(movie!!)
            notifyItemChanged(position)
        } else {
            localRepository.insert(movie!!)
            notifyItemChanged(position)
        }
    }
}