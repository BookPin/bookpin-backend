package com.bookpin.presentation.common

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        log.error("Unexpected error occurred: ${ex.message}", ex)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(
                code = "INTERNAL_SERVER_ERROR",
                message = "서버 내부 오류가 발생했습니다.",
                timestamp = LocalDateTime.now()
            ))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        log.error("Invalid argument: ${ex.message}", ex)

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(
                code = "BAD_REQUEST",
                message = ex.message ?: "잘못된 요청입니다.",
                timestamp = LocalDateTime.now()
            ))
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ErrorResponse> {
        log.error("Invalid state: ${ex.message}", ex)

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorResponse(
                code = "CONFLICT",
                message = ex.message ?: "처리할 수 없는 상태입니다.",
                timestamp = LocalDateTime.now()
            ))
    }
}

data class ErrorResponse(
    val code: String,
    val message: String,
    val timestamp: LocalDateTime
)
