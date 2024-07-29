package com.fancychild.bapsanghead.domain.user.enums

import java.security.InvalidParameterException
import java.util.*

enum class Platform {
    KAKAO, GOOGLE;

    companion object {
        fun fromString(provider: String): Platform {
            val platform = provider.uppercase()

            return when (platform) {
                "KAKAO" -> KAKAO
                "GOOGLE" -> GOOGLE
                else -> throw InvalidParameterException()
            }
        }
    }
}
