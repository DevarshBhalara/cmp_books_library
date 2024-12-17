package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.plcoding.bookpedia.app.Routes
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val repository: BookRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val bookId = savedStateHandle.toRoute<Routes.BookDetail>().id

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state
        .onStart {
            fetchBookDecription()
            observFavoriteState()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )


    fun onAction(action: BookDetailAction) {
        when(action) {
            BookDetailAction.OnBackClick -> {}
            BookDetailAction.OnFavourite -> {
                viewModelScope.launch {
                    if(state.value.isFavourite) {
                        repository.deleteFromFavourite(bookId)
                    } else {
                        state.value.book?.let { book -> repository.markAsFavourite(book) }
                    }
                }
            }
            is BookDetailAction.OnSelectedBookChange -> {
                _state.value = _state.value.copy(
                    book = action.book
                )
            }
        }
    }


    private fun observFavoriteState() {
        repository
            .isFavourite(bookId)
            .onEach { isFavourite ->
                _state.update {
                    it.copy(
                        isFavourite = isFavourite
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun fetchBookDecription() {
        viewModelScope.launch {
            repository
                .getBookDescription(bookId)
                .onSuccess { desc ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(
                                description = desc
                            ),
                            isLoading = false
                        )
                    }
                }
        }
    }
}