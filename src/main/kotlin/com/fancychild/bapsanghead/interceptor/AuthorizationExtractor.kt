package com.fancychild.bapsanghead.interceptor

import com.fancychild.bapsanghead.exception.BaseException
import com.fancychild.bapsanghead.exception.ErrorCode
import com.fancychild.bapsanghead.jwt.JwtValues.JWT_AUTHORIZATION_HEADER
import com.fancychild.bapsanghead.jwt.JwtValues.JWT_AUTHORIZATION_VALUE_PREFIX
import jakarta.servlet.http.HttpServletRequest

object AuthorizationExtractor {
    @JvmStatic
    fun extractAccessToken(request: HttpServletRequest): String {
        val accessToken = request.getHeader(JWT_AUTHORIZATION_HEADER)
                ?: throw BaseException(ErrorCode.AUTHENTICATION_FAILED)

        return accessToken.replace(JWT_AUTHORIZATION_VALUE_PREFIX, "")
    }
}
