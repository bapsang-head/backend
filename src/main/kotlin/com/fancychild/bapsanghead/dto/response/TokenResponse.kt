package com.fancychild.bapsanghead.dto.response

data class TokenResponse(
        val accessToken: String,
        val refreshToken: String,
        val isRegistered: Boolean
){
    companion object{
        fun from(authToken: AuthToken): TokenResponse {
            return TokenResponse(authToken.accessToken, authToken.refreshToken, authToken.isRegistered)
        }
    }
}
