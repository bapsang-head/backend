package com.fancychild.bapsanghead.domain.auth.dto.userinfo

interface OAuth2UserInfoResponse {
    val provider: String

    val providerId: String

    val email: String

    val name: String

    val profileImage: String
}
