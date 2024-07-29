package com.fancychild.bapsanghead.domain.user.entity

import com.fancychild.bapsanghead.domain.BaseEntity
import jakarta.persistence.*

@Entity
data class UserDetails(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private var id: Long,

        private var depositorName: String,

        private var phoneNumber: String,

        private val studentMajor: String,

        private val studentCode: String,

        private val universityEmail: String,
) : BaseEntity()
