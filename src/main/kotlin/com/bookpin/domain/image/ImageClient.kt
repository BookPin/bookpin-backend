package com.bookpin.domain.image

interface ImageClient {

    fun getPresignedUrl(imageName: String, expirationTime: Long): String

}