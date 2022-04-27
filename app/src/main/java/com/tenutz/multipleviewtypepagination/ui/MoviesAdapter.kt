package com.tenutz.multipleviewtypepagination.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tenutz.multipleviewtypepagination.R
import com.tenutz.multipleviewtypepagination.data.datasource.paging.entity.Movies
import com.tenutz.multipleviewtypepagination.databinding.ItemContainerMovieBinding
import com.tenutz.multipleviewtypepagination.databinding.ItemMovieBinding
import com.tenutz.multipleviewtypepagination.databinding.ItemQueryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.RuntimeException

sealed class MoviesItem {
    object SearchItem : MoviesItem()
    data class MovieItem(val movies: PagingData<Movies.Movie>): MoviesItem()
}

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

    data class MovieViewHolder(val binding: ItemContainerMovieBinding): MoviesViewHolder(binding) {

        val adapter = MoviesPagingAdapter()

        init {
            binding.recyclerIcontainermovie.adapter = adapter
        }

        fun bind(movies: MoviesItem.MovieItem) {
            CoroutineScope(Dispatchers.Main).launch {
                adapter.submitData(movies.movies)
            }
        }
    }
}

class MoviesAdapter(val listener: (String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = arrayListOf<MoviesItem>(
        MoviesItem.SearchItem,
        MoviesItem.MovieItem(PagingData.empty())
    )

    fun setMovies(movies: PagingData<Movies.Movie>) {
        items[VIEW_TYPE_MOVIE] = MoviesItem.MovieItem(movies)
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_SEARCH = 0
        const val VIEW_TYPE_MOVIE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_SEARCH -> MoviesViewHolder.SearchViewHolder(viewBind(parent, R.layout.item_query)) {
                listener(it)
            }
            VIEW_TYPE_MOVIE -> MoviesViewHolder.MovieViewHolder(viewBind(parent, R.layout.item_container_movie))
            else -> throw RuntimeException("세상에 이런 일이!")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MoviesViewHolder.MovieViewHolder -> holder.bind(items[position] as MoviesItem.MovieItem)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is MoviesItem.SearchItem -> VIEW_TYPE_SEARCH
            is MoviesItem.MovieItem -> VIEW_TYPE_MOVIE
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