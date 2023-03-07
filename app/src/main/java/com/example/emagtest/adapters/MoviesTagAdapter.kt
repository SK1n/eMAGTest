package com.example.emagtest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emagtest.databinding.ItemTagsBinding
import com.example.emagtest.models.MoviesDetailsModel

class MoviesTagAdapter(
    var list: List<MoviesDetailsModel.Genres>,
) : RecyclerView.Adapter<MoviesTagAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemTagsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTagsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.tagName.text = this.name
                if (position > 1) {
                    holder.itemView.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}