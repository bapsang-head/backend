package com.fancychild.bapsanghead.controller.dto.response

import com.fancychild.bapsanghead.exception.ErrorCode

data class ErrorResponse(
        val code: String,

        val message: String
) {

    companion object {
        fun of(code: String, message: String): ErrorResponse {
            return ErrorResponse(code, message)
        }

        fun of(errorCode: ErrorCode, exception: Throwable? = null): ErrorResponse {
            return ErrorResponse(errorCode.code, exception?.message ?: errorCode.message)
        }
    }
}
