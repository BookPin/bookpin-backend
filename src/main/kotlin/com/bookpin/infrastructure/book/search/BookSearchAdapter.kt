package com.bookpin.infrastructure.book.search

import com.bookpin.domain.book.client.BookSearchClient
import com.bookpin.domain.book.client.BookSearchResult
import com.bookpin.domain.book.repository.BookSearchSource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class BookSearchAdapter(
    private val kakaoBookSearchClient: KakaoBookSearchClient,
    private val naverBookSearchClient: NaverBookSearchClient,
    private val aladinBookSearchClient: AladinBookSearchClient,
    private val googleBookSearchClient: GoogleBookSearchClient,
) : BookSearchClient {

    override suspend fun search(query: String): List<BookSearchResult> = coroutineScope {
        val kakaoDeferred = async { searchKakao(query) }
        val naverDeferred = async { searchNaver(query) }
        val aladinDeferred = async { searchAladin(query) }
        val googleBookSearchClient = async { searchGoogle(query) }

        val kakaoResults = kakaoDeferred.await()
        val naverResults = naverDeferred.await()
        val aladinResults = aladinDeferred.await()
        val googleResults = googleBookSearchClient.await()

        mergeAndDeduplicateResults(kakaoResults, naverResults, aladinResults, googleResults)
    }

    private fun searchKakao(query: String): List<BookSearchResult> {
        return try {
            kakaoBookSearchClient.search(query).documents.map { doc ->
                BookSearchResult(
                    title = doc.title,
                    author = doc.authors.joinToString(", "),
                    imageUrl = doc.thumbnail,
                    totalPage = 0,
                    isbn = doc.isbn,
                    publisher = doc.publisher,
                    source = BookSearchSource.KAKAO
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun searchNaver(query: String): List<BookSearchResult> {
        return try {
            naverBookSearchClient.search(query).items.map { item ->
                BookSearchResult(
                    title = item.title.removeHtmlTags(),
                    author = item.author.removeHtmlTags(),
                    imageUrl = item.image,
                    totalPage = 0,
                    isbn = item.isbn,
                    publisher = item.publisher?.removeHtmlTags(),
                    source = BookSearchSource.NAVER
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun searchAladin(query: String): List<BookSearchResult> {
        return try {
            aladinBookSearchClient.search(query).item.map { item ->
                BookSearchResult(
                    title = item.title,
                    author = item.author,
                    imageUrl = item.cover,
                    totalPage = item.subInfo?.getPageCount() ?: 0,
                    isbn = item.isbn13 ?: item.isbn,
                    publisher = item.publisher,
                    source = BookSearchSource.ALADIN
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun searchGoogle(query: String): List<BookSearchResult> {
        return try {
            googleBookSearchClient.search(query).items.orEmpty()
                .filter { it.volumeInfo != null }
                .map { item ->
                    val volumeInfo = item.volumeInfo!!
                    BookSearchResult(
                        title = volumeInfo.title ?: "",
                        author = volumeInfo.authors.orEmpty().joinToString(", "),
                        imageUrl = volumeInfo.getThumbnail(),
                        totalPage = volumeInfo.getPageCount(),
                        isbn = volumeInfo.getIsbn13(),
                        publisher = volumeInfo.publisher,
                        source = BookSearchSource.GOOGLE
                    )
                }
        } catch (e: Exception) {
            emptyList()
        }
    }


    private fun mergeAndDeduplicateResults(
        vararg results: List<BookSearchResult>,
    ): List<BookSearchResult> {
        val allResults = results.asList()
            .flatten()

        return BookSearchMerger.merge(allResults)
    }

    private fun String.removeHtmlTags(): String {
        return this.replace(Regex("<[^>]*>"), "")
    }
}
