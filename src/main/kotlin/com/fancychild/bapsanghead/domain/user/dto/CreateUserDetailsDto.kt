package com.fancychild.bapsanghead.domain.user.dto

data class CreateUserDetailsDto(
        val depositorName: String,
        val phoneNumber: String,
        val universityEmail: String,
        val studentMajor: String,
        val studentCode: String
)
