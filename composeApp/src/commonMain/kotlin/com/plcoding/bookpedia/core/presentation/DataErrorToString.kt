package com.plcoding.bookpedia.core.presentation

import com.plcoding.bookpedia.core.domain.DataError

fun DataError.toUiText() : UiText {
    val strings = when(this) {
        DataError.Local.DISK_FULL -> "Your Disk is Full"
        DataError.Local.UNKNOWN -> "Oops, Something went wrong!"
        DataError.Remote.REQUEST_TIMEOUT -> "The request timed out"
        DataError.Remote.TOO_MANY_REQUEST -> "Too many request"
        DataError.Remote.NO_INTERNET -> "Please check your internet connection"
        DataError.Remote.SERVER_ERROR -> "Sever side error!"
        DataError.Remote.SERIALIZATION_ERROR -> "Coultn't parse data"
        DataError.Remote.UNKNOWN -> "Oops, Something went wrong!"
    }

    return UiText.DynamicString(strings)
}