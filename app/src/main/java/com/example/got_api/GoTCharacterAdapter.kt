package com.example.got_api

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.got_api.R


class GoTCharacterAdapter(private val characters: List<GoTCharacter>) : RecyclerView.Adapter<GoTCharacterAdapter.ViewHolder>() {

    class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val charImage: ImageView = itemView.findViewById(R.id.char_image)
        val charName: TextView = itemView.findViewById(R.id.tv_char_info_name)
        val charTitle: TextView = itemView.findViewById(R.id.tv_char_info_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_got_character, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.charName.text = character.fullName
        holder.charTitle.text = "Also known as: ${character.title}"

        Glide.with(holder.itemView.context)
            .load(character.imageUrl)
            .fitCenter()
            .into(holder.charImage)
    }
}

data class GoTCharacter(val fullName: String, val title: String, val imageUrl: String)
