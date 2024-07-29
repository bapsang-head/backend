package com.fancychild.bapsanghead.domain.user.service

import com.fancychild.bapsanghead.domain.user.dto.CreateUserDto
import com.fancychild.bapsanghead.domain.user.entity.Users
import com.fancychild.bapsanghead.domain.user.repository.UserRepository
import com.fancychild.bapsanghead.exception.BaseException
import com.fancychild.bapsanghead.exception.ErrorCode
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository
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

    private fun updateProfileOfExistUser(createUserDto: CreateUserDto, existUser: Users): Users {
        existUser.updateProfile(createUserDto.email, createUserDto.name, createUserDto.profileImage)
        userRepository.save(existUser)
        return existUser
    }
}
