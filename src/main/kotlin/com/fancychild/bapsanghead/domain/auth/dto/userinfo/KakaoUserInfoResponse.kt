package com.fancychild.bapsanghead.domain.auth.dto.userinfo

class KakaoUserInfoResponse(
        private val attribute: Map<String, Any>
) : OAuth2UserInfoResponse {

    private val account: Map<String, Any> = attribute["kakao_account"] as? Map<String, Any> ?: emptyMap()
    private val profile: Map<String, Any> = account["profile"] as? Map<String, Any> ?: emptyMap()

    override val provider: String
        get() = "kakao"

    override val providerId: String
        get() = attribute["id"].toString()

    override val email: String
        get() = account["email"].toString()

    override val name: String
        get() = account["name"].toString()

    override val profileImage: String
        get() = profile["thumbnail_image_url"].toString()
}
