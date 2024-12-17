package com.plcoding.bookpedia.book.domain

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(
        query: String,
    ): Result<List<Book>, DataError.Remote>

    suspend fun getBookDescription(
        bookId: String,
    ): Result<String?, DataError>

    fun getFavouriteBooks(): Flow<List<Book>>
    fun isFavourite(id: String): Flow<Boolean>
    suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavourite(id: String)

}