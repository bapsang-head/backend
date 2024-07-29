package com.fancychild.bapsanghead.domain.user.dto

import com.fancychild.bapsanghead.domain.user.enums.Platform
import com.fancychild.bapsanghead.domain.user.enums.UserRole

data class CreateUserDto(
        val name: String,
        val email: String,
        val profileImage: String,
        val platform: Platform,
        val platformId: String,
        val role: UserRole
)
