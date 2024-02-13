package com.practicum.movies.ui.cast

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.practicum.movies.databinding.ListItemHeaderBinding
import com.practicum.movies.presentation.cast.MoviesCastRVItem
import com.practicum.movies.ui.cast.core.RVItem

fun movieCastHeaderDelegate() = adapterDelegateViewBinding<MoviesCastRVItem.HeaderItem, RVItem, ListItemHeaderBinding>(
    { layoutInflater, root -> ListItemHeaderBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.headerTextView.text = item.headerText
    }
}