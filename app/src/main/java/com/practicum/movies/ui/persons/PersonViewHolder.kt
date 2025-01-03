package com.practicum.movies.ui.persons

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.movies.R
import com.practicum.movies.domain.persons.models.Person

class PersonViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_person, parent, false)
    ) {

    var photo: ImageView = itemView.findViewById(R.id.photo)
    var name: TextView = itemView.findViewById(R.id.name)
    var description: TextView = itemView.findViewById(R.id.description)

    fun bind(person: Person) {
        Glide.with(itemView)
            .load(person.photoUrl)
            .placeholder(R.drawable.placeholder)
            .circleCrop()
            .into(photo)

        name.text = person.name
        description.text = person.description
    }
}