package com.fancychild.bapsanghead.domain.user.entity

import com.fancychild.bapsanghead.domain.BaseEntity
import com.fancychild.bapsanghead.domain.user.enums.Platform
import com.fancychild.bapsanghead.domain.user.enums.UserRole
import jakarta.persistence.*

@Entity
data class Users(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
        @JoinColumn(name = "user_details_id")
        var userDetails: UserDetails? = null,

        var name: String,

        var email: String,

        var profileImage: String? = null,

        @Enumerated(EnumType.STRING)
        private val platform: Platform,

        private val platformId: String,

        @Enumerated(EnumType.STRING) val role: UserRole,

        var isRegistered: Boolean = false,
) : BaseEntity() {
    fun updateProfile(email: String, name: String, profileImage: String) {
        this.email = email
        this.name = name
        this.profileImage = profileImage
    }
}
