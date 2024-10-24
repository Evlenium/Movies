package com.practicum.movies.ui.movies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.movies.R
import com.practicum.movies.databinding.FragmentMoviesBinding
import com.practicum.movies.domain.movies.models.Movie
import com.practicum.movies.presentation.movies.MoviesSearchViewModel
import com.practicum.movies.presentation.movies.MoviesState
import com.practicum.movies.ui.details.DetailsFragment
import com.practicum.movies.ui.root.RootActivity
import com.practicum.movies.util.debounce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private var adapter: MoviesAdapter? = null

    private lateinit var onMovieClickDebounce: (Movie) -> Unit

    private val viewModel by viewModel<MoviesSearchViewModel>()

    private lateinit var binding: FragmentMoviesBinding

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textWatcher: TextWatcher

    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onMovieClickDebounce = debounce<Movie>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { movie ->
            findNavController().navigate(
                R.id.action_moviesFragment_to_detailsFragment,
                DetailsFragment.createArgs(movie.id, movie.image)
            )
        }

        adapter = MoviesAdapter(
            object : MoviesAdapter.MovieClickListener {
                override fun onMovieClick(movie: Movie) {
                    (activity as RootActivity).animateBottomNavigationView()
                    onMovieClickDebounce(movie)
                }

                override fun onFavoriteToggleClick(movie: Movie) {
                    viewModel.toggleFavorite(movie)
                }

            }
        )

        placeholderMessage = binding.placeholderMessage
        queryInput = binding.queryInput
        moviesList = binding.locations
        progressBar = binding.progressBar

        moviesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = adapter

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        textWatcher?.let { queryInput.addTextChangedListener(it) }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.observeShowToast().observe(viewLifecycleOwner) {
            showToast(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        moviesList.adapter = null
        textWatcher?.let { queryInput.removeTextChangedListener(it) }
    }

    private fun showToast(additionalMessage: String?) {
        Toast.makeText(requireContext(), additionalMessage, Toast.LENGTH_LONG).show()
    }

    private fun render(state: MoviesState) {
        when (state) {
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Empty -> showEmpty(state.message)
            is MoviesState.Error -> showError(state.errorMessage)
            is MoviesState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        placeholderMessage.text = errorMessage
    }

    private fun showEmpty(emptyMessage: String) {
        showError(emptyMessage)
    }

    private fun showContent(movies: List<Movie>) {
        moviesList.visibility = View.VISIBLE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.GONE

        adapter?.movies?.clear()
        adapter?.movies?.addAll(movies)
        adapter?.notifyDataSetChanged()
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

}