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
import androidx.navigation.fragment.navArgs
import com.app.animego.R
import com.app.animego.data.dto.CharacterData
import com.app.animego.databinding.FragmentAnimeDetailBinding
import com.app.animego.utils.setGlideImage
import com.app.animego.utils.setVisibility
import com.app.animego.utils.showToast
import com.app.animego.view.viewmodel.AnimeDetailsViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class AnimeDetailFragment : Fragment() {
    private lateinit var binding: FragmentAnimeDetailBinding
    private val args: AnimeDetailFragmentArgs by navArgs()
    private val viewModel: AnimeDetailsViewModel by viewModels()
    private var mId: Int? = null

    private lateinit var castAdapter: MainCastAdapter
    private var castList: ArrayList<CharacterData> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        registerFlowCollector()
    }

    private fun init() {

        args.let { arguments ->
            mId = arguments.id
            mId?.let {
                viewModel.getAnimeDetails(it)
                viewModel.getCastList(it)
            }
        }


        binding.rvCast.apply {
            castAdapter = MainCastAdapter(castList)
            adapter = castAdapter
        }
    }

    private fun registerFlowCollector() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.animeDetailsListState.collectLatest { state ->

                        if (state.animeDetailResponse != null) {
                            val data = state.animeDetailResponse
                            with(binding) {
                                progressBar.setVisibility(false)
                                tvTitle.text = data!!.data?.title
                                tvSynopsis.text =
                                    if (data.data?.synopsis.isNullOrEmpty()) getString(R.string.no_overview_available) else data.data?.synopsis

                                tvEpisodeCount.text = data.data?.episodes?.let { epi ->
                                    "$epi ${context?.getString(if (epi > 1) R.string.episodes else R.string.episode)}"
                                }

                                tvDuration.text =
                                    data.data?.duration?.uppercase(Locale.getDefault())

                                tvVoteAverage.text =
                                    String.format(
                                        Locale.getDefault(),
                                        "%.1f",
                                        data.data?.score ?: 0.0
                                    )

                                tvGenres.text = StringBuilder().also { strBuilder ->
                                    data.data?.genres?.forEachIndexed { index, genre ->
                                        strBuilder.append(if (index == 0) genre.name else " | ${genre.name}")
                                    }
                                }.toString()

                                if (data.data?.images?.jpg?.imageUrl != null) {
                                    ivPosterBackground.setGlideImage(
                                        data.data?.images?.jpg?.largeImageUrl ?: ""
                                    )
                                } else {
                                    ivPosterBackground.setImageResource(R.drawable.ic_placeholder)
                                }


                                if (data.data?.trailer?.youtubeId != null) {
                                    lifecycle.addObserver(binding.playerView)
                                    binding.playerView.addYouTubePlayerListener(object :
                                        AbstractYouTubePlayerListener() {
                                        override fun onReady(youTubePlayer: YouTubePlayer) {
                                            youTubePlayer.loadVideo(
                                                data.data?.trailer?.youtubeId!!,
                                                0f
                                            )
                                        }

                                        override fun onError(
                                            youTubePlayer: YouTubePlayer,
                                            error: PlayerConstants.PlayerError
                                        ) {
                                            super.onError(youTubePlayer, error)
                                            binding.playerView.setVisibility(false)
                                            binding.ivPosterImage.setVisibility(true)
                                            if (data.data?.images?.jpg?.imageUrl != null) {
                                                ivPosterImage.setGlideImage(
                                                    data.data?.images?.jpg?.largeImageUrl ?: ""
                                                )
                                            } else {
                                                ivPosterImage.setImageResource(R.drawable.ic_placeholder)
                                            }
                                        }
                                    })
                                } else {
                                    binding.playerView.setVisibility(false)
                                    binding.ivPosterImage.setVisibility(true)
                                    if (data.data?.images?.jpg?.imageUrl != null) {
                                        ivPosterImage.setGlideImage(
                                            data.data?.images?.jpg?.largeImageUrl ?: ""
                                        )
                                    } else {
                                        ivPosterImage.setImageResource(R.drawable.ic_placeholder)
                                    }
                                }

                            }
                        }

                        if (state.error.isNotEmpty()) {
                            requireContext().showToast(state.error)
                            binding.progressBar.setVisibility(false)
                        }

                        if (state.loading) {
                            binding.progressBar.setVisibility(true)
                        }
                    }
                }

                launch {
                    viewModel.castListState.collect { state ->
                        if (state.castList != null) {
                            castList.clear()
                            with(binding) {
                                rvCast.setVisibility(true)
                                if (state.castList!!.data.isNotEmpty()) {
                                    castList.addAll(state.castList!!.data.filter { characterData -> characterData.role == "Main" })
                                    castAdapter.notifyDataSetChanged()

                                    shimmerFrameLayout.root.stopShimmer()
                                    shimmerFrameLayout.root.setVisibility(false)
                                } else {
                                    tvCast.setVisibility(false)
                                    rlCast.setVisibility(false)
                                }
                            }

                        }

                        if (state.error.isNotEmpty()) {
                            requireContext().showToast(state.error)
                            with(binding) {
                                shimmerFrameLayout.root.stopShimmer()
                                shimmerFrameLayout.root.setVisibility(false)
                            }
                        }

                        if (state.loading) {
                            with(binding) {
                                shimmerFrameLayout.root.startShimmer()
                                shimmerFrameLayout.root.setVisibility(true)
                            }
                        }

                    }
                }

            }
        }
    }
}
