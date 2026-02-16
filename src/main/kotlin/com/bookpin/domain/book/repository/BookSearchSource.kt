package com.bookpin.domain.book.repository

enum class BookSearchSource(
    val priority:Int,
) {

    KAKAO(3),
    NAVER(2),
    ALADIN(1),
    ;

}