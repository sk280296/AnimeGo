package com.app.animego.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.app.animego.R
import com.app.animego.data.dto.Data
import com.app.animego.databinding.FragmentTopAnimeBinding
import com.app.animego.utils.setVisibility
import com.app.animego.utils.showToast
import com.app.animego.view.viewmodel.TopAnimeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopAnimeFragment : Fragment() {

    private lateinit var binding: FragmentTopAnimeBinding
    private val searchMovieList = mutableListOf<Data>()
    private lateinit var searchMovieAdapter: TopAnimeMovieAdapter
    private val viewModel: TopAnimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopAnimeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        viewModel.searchMovieList()
        setFlowCollector()
    }

    private fun init() {
        binding.rvMovieList.apply {
            searchMovieAdapter = TopAnimeMovieAdapter(
                requireContext(),
                searchMovieList,
                onItemClicked = { position ->
                    searchMovieList[position].malId?.let { id ->
                        val detailFragment =
                            TopAnimeFragmentDirections.actionTopAnimeFragmentToAnimeDetailFragment(id = id)
                        if (findNavController().currentDestination?.id == R.id.topAnimeFragment) {
                            findNavController().navigate(detailFragment)
                        }
                    }

                }
            )

            adapter = searchMovieAdapter
        }
    }

    private fun setFlowCollector() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.topAnimeListState.collect { state ->
                        if (state.topAnimeResponse != null) {
                            searchMovieList.clear()
                            with(binding) {
                                tvNoData.setVisibility(false)
                                shimmerFrameLayout.root.stopShimmer()
                                shimmerFrameLayout.root.setVisibility(false)
                                rvMovieList.setVisibility(true)
                            }

                            if ((state.topAnimeResponse!!.pagination?.items?.count ?: 0) > 0) {
                                searchMovieList.addAll(state.topAnimeResponse!!.data)
                                searchMovieAdapter.notifyDataSetChanged()
                            } else {
                                binding.tvNoData.setVisibility(true)
                            }
                        }

                        if (state.error.isNotEmpty()) {
                            requireContext().showToast(state.error)
                            with(binding) {
                                shimmerFrameLayout.root.stopShimmer()
                                shimmerFrameLayout.root.setVisibility(false)

                                tvNoData.text = getString(R.string.no_data_found)
                                tvNoData.setVisibility(true)
                            }
                        }

                        if (state.loading) {
                            with(binding) {
                                shimmerFrameLayout.root.startShimmer()
                                shimmerFrameLayout.root.setVisibility(true)
                                rvMovieList.setVisibility(false)
                                tvNoData.setVisibility(false)
                            }
                        }

                    }
                }

            }
        }
    }
}