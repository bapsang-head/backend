package com.fancychild.bapsanghead.filter

import com.fancychild.bapsanghead.domain.user.dto.UserDto
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class JWTTokenAuthentication(
        private val accessToken: String,
        private val userDto: UserDto
) : Authentication {


    override fun getAuthorities(): Collection<GrantedAuthority> {
        val collection: MutableCollection<GrantedAuthority> = ArrayList<GrantedAuthority>()
        collection.add(userDto::role as GrantedAuthority)

        return collection
    }

    override fun getName(): String {
        return java.lang.String.valueOf(userDto.userId)
    }

    override fun getCredentials(): Any {
        return accessToken
    }

    override fun getDetails(): Any? {
        return null
    }

    override fun getPrincipal(): Any {
        return ""
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
    }
}
