package com.tenutz.multipleviewtypepagination.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.paging.insertHeaderItem
import androidx.paging.map
import com.tenutz.multipleviewtypepagination.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding get() = _binding!!

    private val viewModel: MainFViewModel by viewModels()

    private val adapter: MoviesPagingAdapter by lazy {
        MoviesPagingAdapter(lifecycle) {
            viewModel.search(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerMain.adapter = adapter

        adapter.submitData(lifecycle, PagingData.empty<MovieItem>()
            .insertHeaderItem(item = MovieItem.Header))

        viewModel.movies.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData.map { MovieItem.Data(it) as MovieItem }
                .insertHeaderItem(item = MovieItem.Header))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}