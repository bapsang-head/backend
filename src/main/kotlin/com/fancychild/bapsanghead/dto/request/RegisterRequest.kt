package com.fancychild.bapsanghead.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class RegisterRequest(
        @Schema(description = "키", example = "180")
        val height: Int,

        @Schema(description = "몸무게", example = "70")
        val weight: Int,

        @Schema(description = "나이", example = "25")
        val age: Int,

        @Schema(description = "성별", example = "MALE or FEMALE")
        val gender: String,

        @Schema(
                description = "활동량(영어로 보내주세요.)",
                example = "LOW(평소 활동량이 적습니다.) " +
                        "or LIGHT(평소 가볍게 운동합니다.) " +
                        "or MEDIUM(활동적인 편입니다.) " +
                        "or HIGH(운동 없으면 못삽니다.)"
        )
        val activityLevel: String
)
