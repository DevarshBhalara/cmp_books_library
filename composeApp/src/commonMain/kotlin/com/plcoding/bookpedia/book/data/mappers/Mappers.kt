package com.plcoding.bookpedia.book.data.mappers

import com.plcoding.bookpedia.book.data.database.BookEntitiy
import com.plcoding.bookpedia.book.data.dto.SearchResponseDto
import com.plcoding.bookpedia.book.data.dto.SearchedBookDto
import com.plcoding.bookpedia.book.domain.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imageUrl = if(coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        author = authorName ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingAverage,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0,
        ratingCount = ratingCount
    )
}

fun Book.toBookEntity(): BookEntitiy {
    return BookEntitiy(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = author,
        firstPublisher = firstPublishYear,
        ratingCounts = ratingCount,
        averageRating = averageRating,
        numPagesMedian = numPages,
        numEdition = numEditions
    )
}


fun BookEntitiy.toBook(): Book {
    return Book(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        author = authors,
        firstPublishYear = firstPublisher,
        ratingCount = ratingCounts,
        averageRating = averageRating,
        numPages = numPagesMedian,
        numEditions = numEdition
    )
}
