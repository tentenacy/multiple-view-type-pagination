package com.tenutz.multipleviewtypepagination.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tenutz.multipleviewtypepagination.R
import com.tenutz.multipleviewtypepagination.data.datasource.paging.entity.Movies
import com.tenutz.multipleviewtypepagination.databinding.ItemMovieBinding
import com.tenutz.multipleviewtypepagination.databinding.ItemQueryBinding
import java.lang.RuntimeException

sealed class MovieItem(val type: MovieType) {
    data class Data(val value: Movies.Movie): MovieItem(MovieType.DATA)
    object Header: MovieItem(MovieType.HEADER)
    object Separator: MovieItem(MovieType.SEPARATOR)
}

enum class MovieType { HEADER, DATA, SEPARATOR }

sealed class MoviesViewHolder(
    binding: ViewDataBinding,
): RecyclerView.ViewHolder(binding.root) {

    data class SearchViewHolder(val binding: ItemQueryBinding, val listener: (String) -> Unit): MoviesViewHolder(binding) {

        init {
            binding.btnIquerySearch.setOnClickListener {
                listener(binding.editIquery.text.toString())
            }
        }
    }

    data class MovieViewHolder(val lifecycle: Lifecycle, val binding: ItemMovieBinding): MoviesViewHolder(binding) {

        fun bind(movie: Movies.Movie) {
            binding.movie = movie
            Glide.with(binding.root)
                .asBitmap()
                .load(movie.image)
                .into(binding.imageImovie)
        }
    }
}

class MoviesPagingAdapter(val lifecycle: Lifecycle, val listener: (String) -> Unit): PagingDataAdapter<MovieItem, RecyclerView.ViewHolder>(
    COMPARATOR
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType == VIEW_TYPE_SEARCH) return
        getItem(position)?.let {
            when(holder) {
                is MoviesViewHolder.MovieViewHolder -> {
                    holder.bind((it as MovieItem.Data).value)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_SEARCH -> MoviesViewHolder.SearchViewHolder(viewBind(parent, R.layout.item_query)) {
                listener(it)
            }
            VIEW_TYPE_MOVIE -> MoviesViewHolder.MovieViewHolder(lifecycle, viewBind(parent, R.layout.item_movie))
            else -> throw RuntimeException("세상에 이런 일이!")
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.let {
            return if(it.type == MovieType.HEADER) VIEW_TYPE_SEARCH else VIEW_TYPE_MOVIE
        } ?: kotlin.run {
            return VIEW_TYPE_MOVIE
        }
    }

    companion object {

        const val VIEW_TYPE_SEARCH = 0
        const val VIEW_TYPE_MOVIE = 1

        private val COMPARATOR = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return if(oldItem.type == MovieType.DATA && newItem.type == MovieType.DATA) {
                    (oldItem as MovieItem.Data).value.link == (newItem as MovieItem.Data).value.link
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return if(oldItem.type == MovieType.DATA && newItem.type == MovieType.DATA) {
                    (oldItem as MovieItem.Data).value == (newItem as MovieItem.Data).value
                } else {
                    false
                }
            }
        }
    }

    private fun <T: ViewDataBinding> viewBind(parent: ViewGroup, layoutRes: Int): T {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutRes,
            parent,
            false
        )
    }
}