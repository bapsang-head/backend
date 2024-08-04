package com.fancychild.bapsanghead.domain.user.dto

import com.fancychild.bapsanghead.domain.user.entity.UserDetails
import com.fancychild.bapsanghead.domain.user.enums.ActivityLevel

data class UserDetailsDto(

        private val height: Int,

        private val weight: Int,

        private val age: Int,

        private val activityLevel: ActivityLevel
){
    fun toEntity() = UserDetails(
            height = height,
            weight = weight,
            age = age,
            activityLevel = activityLevel
    )
}
