package com.fancychild.bapsanghead.controller.dto.response

data class AuthToken(
        val accessToken: String,
        val refreshToken: String,
        val isRegistered: Boolean
){

    companion object {
        fun of(accessToken: String, refreshToken: String, isRegistered: Boolean): AuthToken {
            return AuthToken(accessToken, refreshToken, isRegistered)
        }
    }
}
