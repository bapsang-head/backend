package com.fancychild.bapsanghead.domain.user.enums

enum class ActivityLevel(
        val message: String
) {
    LOW("평소 활동량이 적습니다."),
    LIGHT("평소 가볍게 운동합니다."),
    MEDIUM("활동적인 편입니다."),
    HIGH("운동 없으면 못삽니다."),
}
