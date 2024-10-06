package com.fancychild.bapsanghead.domain.auth.service

import com.fancychild.bapsanghead.domain.auth.dto.userinfo.OAuth2UserInfoResponse
import com.fancychild.bapsanghead.domain.auth.util.OAuth2UserInfoManager
import com.fancychild.bapsanghead.domain.user.dto.CreateUserDto
import com.fancychild.bapsanghead.domain.user.entity.Users
import com.fancychild.bapsanghead.domain.user.enums.Platform
import com.fancychild.bapsanghead.domain.user.enums.UserRole
import com.fancychild.bapsanghead.domain.user.service.UserService
import com.fancychild.bapsanghead.controller.dto.response.AuthToken
import com.fancychild.bapsanghead.util.AuthTokenGenerator
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val userService: UserService,
        private val authTokenGenerator: AuthTokenGenerator,
        private val oAuth2UserInfoManager: OAuth2UserInfoManager
) {
    fun login(platform: Platform, tokenType: String, accessToken: String): AuthToken {

        val userInfo: OAuth2UserInfoResponse = oAuth2UserInfoManager.getUserInfo(platform, tokenType, accessToken)

        val newUser: Users = userService.saveUser(generateCreateUserDto(userInfo))
        val authToken = authTokenGenerator.generateAuthToken(newUser)

        return authToken
    }

    private fun generateCreateUserDto(userInfo: OAuth2UserInfoResponse): CreateUserDto {
        return CreateUserDto(
                name = userInfo.name,
                email = userInfo.email,
                platform = Platform.fromString(userInfo.provider),
                platformId = userInfo.providerId,
                role = UserRole.ROLE_USER,
                profileImage = userInfo.profileImage
        )
    }
}
