package com.fancychild.bapsanghead.dto.response

data class UserResponse(
        val name: String,
        val email: String,
        val age: Int,
        val weight: Int,
        val height: Int,
        val gender: String,
        val activityLevel: String,
)
