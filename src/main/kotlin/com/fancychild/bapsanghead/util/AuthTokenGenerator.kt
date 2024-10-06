package com.fancychild.bapsanghead.util

import com.fancychild.bapsanghead.domain.user.dto.UserDto
import com.fancychild.bapsanghead.domain.user.entity.Users
import com.fancychild.bapsanghead.controller.dto.response.AuthToken
import com.fancychild.bapsanghead.jwt.JwtAuthTokenUtil
import org.springframework.stereotype.Component

@Component
class AuthTokenGenerator(private val jwtAuthTokenUtil: JwtAuthTokenUtil) {
    fun generateAuthToken(user: Users): AuthToken {
        val userDto = generateUserDto(user)

        val userId = userDto.userId
        val name = userDto.name
        val role = userDto.role
        val isRegistered = userDto.isRegistered

        val newAccessToken = jwtAuthTokenUtil.createAccessToken(userId, name, role, isRegistered)
        val newRefreshToken = jwtAuthTokenUtil.createRefreshToken()

        return AuthToken.of(newAccessToken, newRefreshToken, isRegistered)
    }

    private fun generateUserDto(user: Users): UserDto {
        return UserDto(
                userId = user.id,
                name = user.name,
                role = user.role.toString(),
                isRegistered = user.isRegistered
        )
    }
}
