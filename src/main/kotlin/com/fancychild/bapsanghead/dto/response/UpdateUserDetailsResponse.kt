package com.fancychild.bapsanghead.dto.response

import com.fancychild.bapsanghead.domain.user.dto.UserDetailsDto
import com.fancychild.bapsanghead.domain.user.enums.ActivityLevel
import com.fancychild.bapsanghead.domain.user.enums.Gender

data class UpdateUserDetailsResponse(
        val height: Int?,

        val weight: Int?,

        val age: Int?,

        val gender: Gender?,

        val activityLevel: ActivityLevel?
){
    companion object{
        fun of(dto: UserDetailsDto): UpdateUserDetailsResponse{
            return UpdateUserDetailsResponse(
                    height = dto.height,
                    weight = dto.weight,
                    age = dto.age,
                    gender = dto.gender,
                    activityLevel = dto.activityLevel
            )
        }
    }
}
