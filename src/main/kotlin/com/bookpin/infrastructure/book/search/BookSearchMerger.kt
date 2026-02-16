package com.bookpin.infrastructure.book.search

import com.bookpin.domain.book.client.BookSearchResult

object BookSearchMerger {

    fun merge(results: List<BookSearchResult>): List<BookSearchResult> {
        return results.groupBy { it.isbn }
            .map { it.value.sortedBy { searchResult -> searchResult.source.priority }[0] }
    }

}