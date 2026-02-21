package com.bookpin.infrastructure.book.search

import com.bookpin.domain.book.client.BookSearchResult

object BookSearchMerger {

    fun merge(results: List<BookSearchResult>): List<BookSearchResult> {
        return results
            .filter { it.isbn != null }
            .groupBy { it.isbn }
            .map { it.value.sortedBy { searchResult -> searchResult.source.priority }[0] }
            .sortedBy { it.source.priority }
    }

}