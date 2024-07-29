package com.fancychild.bapsanghead.filter

import com.fancychild.bapsanghead.dto.response.ErrorResponse
import com.fancychild.bapsanghead.exception.ErrorCode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class JwtAccessDeniedHandler(
        private val objectMapper: ObjectMapper
) : AccessDeniedHandler {

    override fun handle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            accessDeniedException: AccessDeniedException
    ) {
        writeErrorResponse(response)
    }

    private fun writeErrorResponse(
            response: HttpServletResponse
    ) {
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.writer.write(objectMapper.writeValueAsString(ErrorResponse.of(ErrorCode.AUTHORIZATION_FAILED)))
    }
}
