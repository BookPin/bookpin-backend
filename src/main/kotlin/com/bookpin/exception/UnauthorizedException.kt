package com.bookpin.exception

class UnauthorizedException : Exception {

    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)

}