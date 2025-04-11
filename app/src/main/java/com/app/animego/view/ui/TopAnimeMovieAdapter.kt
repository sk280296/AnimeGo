package com.app.animego.view.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.animego.R
import com.app.animego.data.dto.Data
import com.app.animego.databinding.ItemAnimeBinding
import com.app.animego.utils.setGlideImage
import java.util.Locale

class TopAnimeMovieAdapter(
    private val context: Context,
    private val movies: List<Data>,
    private val onItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<TopAnimeMovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: ItemAnimeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        with(holder.binding) {
            tvMovieTitle.text = movie.title
            layoutRating.tvVoteAverage.text =
                String.format(Locale.getDefault(), "%.1f", movie.score ?: 0.0)
            tvReleaseYear.text = movie.episodes?.let { epi ->
                "$epi ${context.getString(if (epi > 1) R.string.episodes else R.string.episode)}"
            }

            if (movie.url != null) {
                ivPosterImage.setGlideImage(movie.images?.jpg?.imageUrl ?: "")
            } else {
                ivPosterImage.setImageResource(R.drawable.ic_placeholder)
            }

            holder.binding.root.setOnClickListener {
                onItemClicked.invoke(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
