package com.bookpin.presentation.image

import com.bookpin.app.image.ImageService
import com.bookpin.app.image.response.PresignedResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/image")
class ImageController(
    private val imageService: ImageService,
) {

    @GetMapping("/presigned-url")
    fun getBucketDomain(imageExtension: String): ResponseEntity<PresignedResponse> {
        val tenMinute = 10L
        val createPresignedUrl = imageService.createPresignedUrl(imageExtension, tenMinute)

        return ResponseEntity.ok(createPresignedUrl)
    }

}