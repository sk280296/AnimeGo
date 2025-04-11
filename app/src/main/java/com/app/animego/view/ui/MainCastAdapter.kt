package com.app.animego.view.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.animego.R
import com.app.animego.data.dto.CharacterData
import com.app.animego.databinding.ItemCastBinding
import com.app.animego.utils.setGlideImage


class MainCastAdapter(
    private val list: List<CharacterData>,
) : RecyclerView.Adapter<MainCastAdapter.MovieViewHolder>() {

    class MovieViewHolder(val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemCastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = list[position]
        with(holder.binding) {
            if (data.voiceActors[0].person?.images?.jpg?.imageUrl != null) {
                ivPosterImage.setGlideImage(
                    data.voiceActors[0].person?.images?.jpg?.imageUrl!!
                )
            } else {
                ivPosterImage.setImageResource(R.drawable.ic_placeholder)
            }
            data.character?.name.let { name ->
                tvCharacterName.text = name
            }
            data.voiceActors[0].person?.name.let { name ->
                val split = name?.split(",")
                if (split?.size!! > 1) {
                    val fistName = split[1]
                    val lastName = split[0]
                    tvRealName.text = "$fistName  $lastName"
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
