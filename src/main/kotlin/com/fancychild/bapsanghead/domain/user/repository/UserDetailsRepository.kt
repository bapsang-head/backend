package com.fancychild.bapsanghead.domain.user.repository

import com.fancychild.bapsanghead.domain.user.entity.UserDetails
import org.springframework.data.jpa.repository.JpaRepository

interface UserDetailsRepository: JpaRepository<UserDetails, Long>{
}
