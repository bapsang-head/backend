package com.fancychild.bapsanghead.domain.user.dto

import com.fancychild.bapsanghead.domain.user.entity.UserDetails
import com.fancychild.bapsanghead.domain.user.enums.ActivityLevel
import com.fancychild.bapsanghead.domain.user.enums.Gender

data class UserDetailsDto(

        val height: Int?,

        val weight: Int?,

        val age: Int?,

        val gender: Gender?,

        val activityLevel: ActivityLevel?
) {
    fun toEntity() = UserDetails(
            height = height,
            weight = weight,
            age = age,
            gender = gender,
            activityLevel = activityLevel
    )
}
