package com.bookpin.infrastructure.book.search

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean

class KakaoFeignConfig(
    private val properties: BookSearchProperties
) {
    @Bean
    fun kakaoRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor { template ->
            template.header("Authorization", "KakaoAK ${properties.kakao.apiKey}")
        }
    }
}

class NaverFeignConfig(
    private val properties: BookSearchProperties
) {
    @Bean
    fun naverRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor { template ->
            template.header("X-Naver-Client-Id", properties.naver.clientId)
            template.header("X-Naver-Client-Secret", properties.naver.clientSecret)
        }
    }
}

class AladinFeignConfig(
    private val properties: BookSearchProperties
) {
    @Bean
    fun aladinRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor { template ->
            template.query("ttbkey", properties.aladin.ttbKey)
        }
    }
}
