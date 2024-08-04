package com.fancychild.bapsanghead.domain.user.service

import com.fancychild.bapsanghead.domain.user.dto.CreateUserDto
import com.fancychild.bapsanghead.domain.user.dto.UserDetailsDto
import com.fancychild.bapsanghead.domain.user.entity.UserDetails
import com.fancychild.bapsanghead.domain.user.entity.Users
import com.fancychild.bapsanghead.domain.user.repository.UserDetailsRepository
import com.fancychild.bapsanghead.domain.user.repository.UserRepository
import com.fancychild.bapsanghead.dto.response.AuthToken
import com.fancychild.bapsanghead.exception.BaseException
import com.fancychild.bapsanghead.exception.ErrorCode
import com.fancychild.bapsanghead.util.AuthTokenGenerator
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository,
        private val userDetailsRepository: UserDetailsRepository,
        private val authTokenGenerator: AuthTokenGenerator
) {

    fun saveUser(dto: CreateUserDto): Users {
        userRepository.findByPlatformAndPlatformId(
                platform = dto.platform,
                platformId = dto.platformId
        )?.let {
            return updateProfileOfExistUser(dto, it)
        }

        val newUser = Users(
                name = dto.name,
                email = dto.email,
                profileImage = dto.profileImage,
                platform = dto.platform,
                platformId = dto.platformId,
                role = dto.role,
                isRegistered = false
        )
        return userRepository.save(newUser)
    }

    fun findById(id: Long): Users {
        return userRepository.findById(id).orElseThrow { throw BaseException(ErrorCode.NOT_FOUND_USER) }
    }

    fun register(userId: Long, userDetailsDto: UserDetailsDto): AuthToken {
        val user = findById(userId)
        if (user.isRegistered) {
            throw BaseException(ErrorCode.ALREADY_REGISTERED_USER)
        }
        val userDetails = userDetailsRepository.save(userDetailsDto.toEntity())

        user.register(userDetails)
        userRepository.save(user)

        return authTokenGenerator.generateAuthToken(user)
    }

    fun updateProfile(userId: Long, userDetailsDto: UserDetailsDto): UserDetails {
        val user = findById(userId)
        val userDetails = user.userDetails

        return userDetails?.run {
            this.update(userDetailsDto)
            userDetailsRepository.save(this)
        }?: throw BaseException(ErrorCode.NOT_FOUND_USER_DETAILS)
    }

    private fun updateProfileOfExistUser(createUserDto: CreateUserDto, existUser: Users): Users {
        existUser.updateProfile(createUserDto.email, createUserDto.name, createUserDto.profileImage)
        userRepository.save(existUser)
        return existUser
    }
}
