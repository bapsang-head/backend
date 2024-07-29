package com.fancychild.bapsanghead.filter

import com.fancychild.bapsanghead.domain.user.dto.UserDto
import com.fancychild.bapsanghead.dto.response.ErrorResponse
import com.fancychild.bapsanghead.exception.BaseException
import com.fancychild.bapsanghead.exception.ErrorCode
import com.fancychild.bapsanghead.jwt.JwtAuthTokenUtil
import com.fancychild.bapsanghead.jwt.JwtValues.JWT_AUTHORIZATION_HEADER
import com.fancychild.bapsanghead.jwt.JwtValues.JWT_AUTHORIZATION_VALUE_PREFIX
import com.fancychild.bapsanghead.jwt.JwtValues.JWT_PAYLOAD_VALUE_ACCESS
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets


@Component
class JwtFilter(
        private val jwtAuthTokenUtil: JwtAuthTokenUtil,
        private val tokenValidator: TokenValidator,
        private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse,
                                  filterChain: FilterChain) {
        var accessToken = request.getHeader(JWT_AUTHORIZATION_HEADER)

        if (accessToken == null) {
            filterChain.doFilter(request, response)
            return
        }
        accessToken = accessToken.replace(JWT_AUTHORIZATION_VALUE_PREFIX, "")

        if (validateAccessToken(response, accessToken)) {
            val authentication: Authentication = JWTTokenAuthentication(accessToken, generateUserDto(accessToken))
            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        }
    }

    @Throws(IOException::class)
    private fun validateAccessToken(response: HttpServletResponse, accessToken: String): Boolean {
        try {
            tokenValidator.validateExpiredToken(accessToken)
            tokenValidator.validateTokenSignature(accessToken)
            tokenValidator.validateTokenCategory(JWT_PAYLOAD_VALUE_ACCESS, accessToken)
        } catch (exception: BaseException) {
            val errorCode: ErrorCode = exception.errorCode
            writeErrorResponse(response, errorCode)
            return false
        }
        return true
    }

    @Throws(IOException::class)
    private fun writeErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer
                .write(objectMapper.writeValueAsString(ErrorResponse.of(errorCode)))
    }

    private fun generateUserDto(accessToken: String): UserDto {
        val userId: Long = jwtAuthTokenUtil.getId(accessToken)
        val name: String = jwtAuthTokenUtil.getName(accessToken)
        val role: String = jwtAuthTokenUtil.getRole(accessToken)

        return UserDto(
                userId = userId,
                name = name,
                role = role
        )
    }
}
