package com.fancychild.bapsanghead.domain.auth.util

import com.fancychild.bapsanghead.domain.auth.dto.userinfo.KakaoUserInfoResponse
import com.fancychild.bapsanghead.domain.auth.dto.userinfo.OAuth2UserInfoResponse
import com.fancychild.bapsanghead.domain.auth.properties.AppProperties
import com.fancychild.bapsanghead.domain.user.enums.Platform
import com.fancychild.bapsanghead.exception.BaseException
import com.fancychild.bapsanghead.exception.ErrorCode
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriBuilder
import java.net.URI

@Component
class OAuth2UserInfoManager(private val appProperties: AppProperties) : OAuth2Manager() {
    fun getUserInfo(
            platform: Platform,
            tokenType: String,
            accessToken: String
    ): OAuth2UserInfoResponse {
        if (platform == Platform.KAKAO) {
            return getKakaoUserInfoResponse(tokenType, accessToken)
        }
        throw BaseException(ErrorCode.INVALID_PLATFORM)
    }

    private fun getKakaoUserInfoResponse(tokenType: String, accessToken: String): OAuth2UserInfoResponse {
        val restClient = createRestClient(appProperties.kakao.userInfoUri)
        val authorization = java.lang.String.join(" ", tokenType, accessToken)

        requestUserInfoToKakao(restClient, authorization)?.run {
            return KakaoUserInfoResponse(this)
        }
        throw BaseException(ErrorCode.FAIL_REQUEST_TO_OAUTH2)
    }

    private fun requestUserInfoToKakao(restClient: RestClient, authorization: String): Map<String, Any>? {
        return restClient
                .post()
                .uri { uriBuilder: UriBuilder -> this.getKakaoUserInfoUri(uriBuilder) }
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .header(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE)
                .retrieve()
                .body(object : ParameterizedTypeReference<Map<String, Any>>() {})
    }

    private fun getKakaoUserInfoUri(uriBuilder: UriBuilder): URI {
        return uriBuilder
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", appProperties.kakao.clientId)
                .queryParam("client_secret", appProperties.kakao.clientSecret)
                .queryParam("property_keys", "[\"kakao_account.email\", \"kakao_account.name\", \"kakao_account.profile\"]")
                .build()
    }
}
