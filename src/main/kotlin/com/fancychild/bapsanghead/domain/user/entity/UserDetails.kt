package com.fancychild.bapsanghead.domain.user.entity

import com.fancychild.bapsanghead.domain.BaseEntity
import com.fancychild.bapsanghead.domain.user.dto.UserDetailsDto
import com.fancychild.bapsanghead.domain.user.enums.ActivityLevel
import com.fancychild.bapsanghead.domain.user.enums.Gender
import jakarta.persistence.*

@Entity
data class UserDetails(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private val id: Long = 0,

        private var height: Int?,

        private var weight: Int?,

        private var age: Int?,

        @Enumerated(EnumType.STRING)
        private var gender: Gender?,

        @Enumerated(EnumType.STRING)
        private var activityLevel: ActivityLevel?
) : BaseEntity() {
    fun update(dto: UserDetailsDto) {
        dto.height?.apply { height = this }
        dto.weight?.apply { weight = this }
        dto.age?.apply { age = this }
        dto.gender?.apply { gender = this }
        dto.activityLevel?.apply { activityLevel = this }
    }

    fun toDto(): UserDetailsDto {
        return UserDetailsDto(
                height = height,
                weight = weight,
                age = age,
                gender = gender,
                activityLevel = activityLevel
        )
    }
}
