package com.bookpin.presentation.test

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginTestController(
    @Value("\${oauth.kakao.client-id}") private val kakaoClientId: String,
    @Value("\${oauth.kakao.javascript-key}") private val kakaoJavascriptKey: String,
    @Value("\${oauth.kakao.redirect-uri}") private val kakaoRedirectUri: String,
    @Value("\${oauth.apple.client-id}") private val appleClientId: String,
    @Value("\${oauth.apple.redirect-uri:http://localhost:8080/test/apple/callback}") private val appleRedirectUri: String
) {

    @GetMapping("/test/login")
    fun loginTestPage(model: Model): String {
        model.addAttribute("kakaoJavascriptKey", kakaoJavascriptKey)
        model.addAttribute("kakaoRedirectUri", kakaoRedirectUri)
        model.addAttribute("appleClientId", appleClientId)
        model.addAttribute("appleRedirectUri", appleRedirectUri)
        return "login-test"
    }

    @GetMapping("/test/kakao/callback")
    fun kakaoCallback(model: Model): String {
        model.addAttribute("kakaoClientId", kakaoClientId)
        return "kakao-callback"
    }

}
