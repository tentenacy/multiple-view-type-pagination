package com.tenutz.multipleviewtypepagination.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.tenutz.multipleviewtypepagination.data.datasource.paging.entity.Movies
import com.tenutz.multipleviewtypepagination.data.datasource.paging.repository.MoviePagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainFViewModel @Inject constructor(
    private val moviePagingRepository: MoviePagingRepository,
): BaseViewModel() {

    private val _movies = MutableLiveData<PagingData<Movies.Movie>>()
    val movies: LiveData<PagingData<Movies.Movie>> = _movies

    fun search(query: String) {
        moviePagingRepository.search(query)
            .observeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({
                _movies.postValue(it)
            }) { t ->
                Logger.e("${t}")

            }.addTo(compositeDisposable)
    }
}