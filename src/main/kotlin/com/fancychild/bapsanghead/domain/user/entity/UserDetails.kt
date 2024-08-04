package com.fancychild.bapsanghead.domain.user.entity

import com.fancychild.bapsanghead.domain.BaseEntity
import com.fancychild.bapsanghead.domain.user.enums.ActivityLevel
import jakarta.persistence.*

@Entity
data class UserDetails(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private val id: Long = 0,

        private val height: Int,

        private val weight: Int,

        private val age: Int,

        private val activityLevel: ActivityLevel
) : BaseEntity()
