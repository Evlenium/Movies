package com.practicum.movies.presentation.persons

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.movies.R
import com.practicum.movies.domain.persons.api.NamesInteractor
import com.practicum.movies.domain.persons.models.Person
import com.practicum.movies.presentation.movies.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NamesViewModel(
    private val context: Context,
    private val namesInteractor: NamesInteractor,
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
    }

    private val handler = Handler(Looper.getMainLooper())

    private val stateLiveData = MutableLiveData<NamesState>()
    fun observeState(): LiveData<NamesState> = stateLiveData

    private val showToast = SingleLiveEvent<String?>()
    fun observeShowToast(): LiveData<String?> = showToast

    private var latestSearchText: String? = null

    private var searchJob: Job? = null

    override fun onCleared() {
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchRequest(changedText)
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {

            renderState(NamesState.Loading)

            viewModelScope.launch {
                namesInteractor
                    .searchNames(newSearchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }

    }

    private fun processResult(foundNames: List<Person>?, errorMessage: String?) {
        val persons = mutableListOf<Person>()
        if (foundNames != null) {
            persons.addAll(foundNames)
        }

        when {
            errorMessage != null -> {
                renderState(
                    NamesState.Error(
                        message = context.getString(
                            R.string.something_went_wrong
                        )
                    )
                )
                showToast.postValue(errorMessage)
            }

            persons.isEmpty() -> {
                renderState(NamesState.Empty(message = context.getString(R.string.nothing_found)))
            }

            else -> {
                renderState(NamesState.Content(persons = persons))
            }
        }
    }

    private fun renderState(state: NamesState) {
        stateLiveData.postValue(state)
    }
}