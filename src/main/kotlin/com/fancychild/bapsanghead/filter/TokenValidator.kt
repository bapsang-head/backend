package com.fancychild.bapsanghead.filter


import com.fancychild.bapsanghead.exception.BaseException
import com.fancychild.bapsanghead.exception.ErrorCode
import com.fancychild.bapsanghead.jwt.JwtAuthTokenUtil
import org.springframework.stereotype.Component

@Component
class TokenValidator(
        val jwtAuthTokenUtil: JwtAuthTokenUtil
) {

    fun validateTokenSignature(token: String?) {
        if (!jwtAuthTokenUtil.isValidToken(token)) {
            throw BaseException(ErrorCode.INVALID_TOKEN)
        }
    }

    fun validateExpiredToken(token: String?) {
        if (jwtAuthTokenUtil.isExpired(token)) {
            throw BaseException(ErrorCode.TOKEN_EXPIRED)
        }
    }

    fun validateTokenCategory(category: String, token: String?) {
        val tokenCategory: String = jwtAuthTokenUtil.getCategory(token)
        if (tokenCategory != category) {
            throw BaseException(ErrorCode.NOT_MATCH_CATEGORY)
        }
    }

    fun checkNotExpiredToken(token: String?) {
        if (!jwtAuthTokenUtil.isExpired(token)) {
            throw BaseException(ErrorCode.TOKEN_NOT_EXPIRED)
        }
    }

    fun validateRegistered(accessToken: String?) {
        if (!jwtAuthTokenUtil.isRegistered(accessToken)) {
            throw BaseException(ErrorCode.NOT_REGISTERED_USER)
        }
    }
}
