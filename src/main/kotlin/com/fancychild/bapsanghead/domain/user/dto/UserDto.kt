package com.fancychild.bapsanghead.domain.user.dto

data class UserDto(
        val userId: Long,
        val name: String,
        val role: String,
        val isRegistered: Boolean = false
)
