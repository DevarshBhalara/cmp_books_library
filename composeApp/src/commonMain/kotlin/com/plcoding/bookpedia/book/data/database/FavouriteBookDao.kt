package com.plcoding.bookpedia.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteBookDao {

    @Upsert
    suspend fun upsert(book: BookEntitiy)

    @Query("select * from BookEntitiy")
    fun getFavBooks(): Flow<List<BookEntitiy>>

    @Query("select * from BookEntitiy where id = :id")
    suspend fun getFavBook(id: String): BookEntitiy?

    @Query("delete from BookEntitiy where id = :id")
    suspend fun deleteFavBook(id: String)
}