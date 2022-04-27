package com.tenutz.multipleviewtypepagination.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tenutz.multipleviewtypepagination.data.datasource.paging.entity.Movies
import com.tenutz.multipleviewtypepagination.databinding.ItemMovieBinding

class MoviesPagingAdapter: PagingDataAdapter<Movies.Movie, MoviesPagingAdapter.ViewHolder>(
    COMPARATOR
) {

    inner class ViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movies.Movie) {
            binding.movie = movie
            Glide.with(binding.root)
                .load(movie.image)
                .into(binding.imageImovie)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movies.Movie>() {
            override fun areItemsTheSame(oldItem: Movies.Movie, newItem: Movies.Movie): Boolean {
                return oldItem.link == newItem.link
            }

            override fun areContentsTheSame(oldItem: Movies.Movie, newItem: Movies.Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}