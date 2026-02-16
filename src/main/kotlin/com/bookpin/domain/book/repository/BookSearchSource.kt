package com.bookpin.domain.book.repository

enum class BookSearchSource(
    val priority:Int,
) {

    KAKAO(4),
    NAVER(3),
    ALADIN(2),
    GOOGLE(1),
    ;

}