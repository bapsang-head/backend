package com.fancychild.bapsanghead.controller

import com.fancychild.bapsanghead.config.userid.LoginUserId
import com.fancychild.bapsanghead.domain.user.dto.CreateUserDetailsDto
import com.fancychild.bapsanghead.domain.user.dto.CreateUserDto
import com.fancychild.bapsanghead.domain.user.dto.UserDto
import com.fancychild.bapsanghead.domain.user.entity.Users
import com.fancychild.bapsanghead.domain.user.enums.Platform
import com.fancychild.bapsanghead.domain.user.enums.UserRole
import com.fancychild.bapsanghead.domain.user.service.UserService
import com.fancychild.bapsanghead.dto.response.AuthToken
import com.fancychild.bapsanghead.dto.response.TokenResponse
import com.fancychild.bapsanghead.jwt.JwtAuthTokenUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/dev")
class DevController(
        private val userService: UserService,
        private val jwtAuthTokenUtil: JwtAuthTokenUtil
) {

    @GetMapping("/token")
    @Operation(summary = "토큰 강제 발행", description = "토큰을 발급합니다.")
    fun getToken(): ResponseEntity<TokenResponse>{
        val user = userService.saveUser(generateCreateUserDto())

        val findUser: Users = userService.findById(user.id)
        val userDto = generateUserDto(findUser)

        val isRegistered: Boolean = userDto.isRegistered

        val newAccessToken = jwtAuthTokenUtil.createAccessToken(
                userDto.userId,
                userDto.name,
                userDto.role,
                isRegistered)
        val newRefreshToken = jwtAuthTokenUtil.createRefreshToken()

        val authToken: AuthToken = AuthToken.of(newAccessToken, newRefreshToken, isRegistered)
        return ResponseEntity.ok(TokenResponse.from(authToken))
    }

    @GetMapping("/user")
    @Operation(summary = "유저 정보 조회", description = "유저 정보를 조회합니다.")
    @SecurityRequirement(name = "JWT")
    fun getUsers(
            @LoginUserId
            @Parameter(hidden = true)
            userId: Long
    ): ResponseEntity<UserDto> {
        val findUser: Users = userService.findById(userId)
        return ResponseEntity.ok(generateUserDto(findUser))
    }

    private fun generateUserDto(user: Users): UserDto {
        return UserDto(
                userId = user.id,
                name = user.name,
                role = user.role.toString(),
                isRegistered = user.isRegistered
        )
    }

    private fun generateCreateUserDto(): CreateUserDto {
        return CreateUserDto(
                name = "테스트",
                email = "abc@naver.com",
                platform = Platform.KAKAO,
                platformId = "1234",
                role = UserRole.ROLE_USER,
                profileImage = ""
        )
    }

    private fun generateCreateUserDetailsDto(): CreateUserDetailsDto {
        return CreateUserDetailsDto(
                depositorName = "홍길동",
                universityEmail = "abc@konkuk.ac.kr",
                phoneNumber = "01012341234",
                studentMajor = "컴퓨터공학부",
                studentCode = "202411032"
        )
    }
}
