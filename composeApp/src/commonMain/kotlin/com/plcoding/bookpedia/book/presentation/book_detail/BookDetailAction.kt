package com.plcoding.bookpedia.book.presentation.book_detail

import com.plcoding.bookpedia.book.domain.Book

sealed interface BookDetailAction {

    data object OnBackClick: BookDetailAction
    data object OnFavourite: BookDetailAction
    data class OnSelectedBookChange(val book: Book) : BookDetailAction


}