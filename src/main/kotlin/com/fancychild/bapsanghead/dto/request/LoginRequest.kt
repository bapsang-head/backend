package com.fancychild.bapsanghead.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import org.jetbrains.annotations.NotNull

data class LoginRequest(
        @Schema(description = "토큰 타입", example = "Bearer")
        @NotNull( "tokenType 는 null 일 수 없습니다.")
        val tokenType: String,

        @Schema(description = "엑세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX1VTRVIiLCJpYXQiOjE2MjYwNjIwMzMsImV4cCI6MTYyNjA2MjAzNH0.1")
        @NotNull("accessToken 은 null 일 수 없습니다.")
        val accessToken: String
)
