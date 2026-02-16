package com.bookpin.app.image

import com.bookpin.app.image.response.PresignedResponse
import com.bookpin.domain.image.ImageClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class ImageService(
    private val imageClient: ImageClient,
    @Value("\${aws.cloud-front.url}")
    private val cloudFrontUrl: String,
) {

    fun createPresignedUrl(imageExtension: String, expirationTime: Long): PresignedResponse {
        val prefix = UUID.randomUUID()
            .toString()
            .replace("-", "")
        val imageName = "$prefix.$imageExtension"
        val imageUrl = "$cloudFrontUrl$imageName"
        val url = imageClient.getPresignedUrl(imageName, expirationTime)

        return PresignedResponse(url, imageUrl)
    }
}