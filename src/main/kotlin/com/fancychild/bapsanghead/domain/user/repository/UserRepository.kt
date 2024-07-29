package com.fancychild.bapsanghead.domain.user.repository

import com.fancychild.bapsanghead.domain.user.entity.Users
import com.fancychild.bapsanghead.domain.user.enums.Platform
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<Users, Long> {

    fun findByPlatformAndPlatformId(platform: Platform, platformId: String): Users?
}
