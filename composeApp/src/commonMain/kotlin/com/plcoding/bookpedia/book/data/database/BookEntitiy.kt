package com.plcoding.bookpedia.book.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntitiy(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String,
    val languages: List<String>,
    val authors: List<String>,
    val firstPublisher: String,
    val ratingCounts: Int?,
    val averageRating: Double?,
    val numPagesMedian: Int?,
    val numEdition: Int?
)