package com.fancychild.bapsanghead.dto.response

import com.fancychild.bapsanghead.domain.user.entity.Users

data class AuthResponse(
        val userId: Long,
        val name: String,
        val accessToken: String,
        val refreshToken: String,
        val isRegistered: Boolean
) {
    companion object {
        fun of(loginUser: Users, authToken: AuthToken): AuthResponse {
            return AuthResponse(
                    userId = loginUser.id,
                    name = loginUser.name,
                    accessToken = authToken.accessToken,
                    refreshToken = authToken.refreshToken,
                    isRegistered = authToken.isRegistered
            )
        }
    }
}
