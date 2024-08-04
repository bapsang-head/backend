package com.fancychild.bapsanghead.dto.request

data class UpdateUserDetailsRequest(
        val height: Int? = null,
        val weight: Int? = null,
        val age: Int? = null,
        val gender: String? = null,
        val activityLevel: String? = null
)
