package com.fancychild.bapsanghead.controller

import com.fancychild.bapsanghead.controller.dto.response.ErrorResponse
import com.fancychild.bapsanghead.controller.dto.response.ErrorResponse.Companion.of
import com.fancychild.bapsanghead.exception.BaseException
import com.fancychild.bapsanghead.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.CharBuffer
import java.security.InvalidParameterException

@RestControllerAdvice
class WebExceptionHandler {
    private val log: Logger = LoggerFactory.getLogger(WebExceptionHandler::class.java)

    @ExceptionHandler(Exception::class)
    fun unKnownException(request: HttpServletRequest,
                         exception: Exception): ResponseEntity<ErrorResponse> {
        return handleUnhandledException(request, exception)
    }

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(request: HttpServletRequest,
                            exception: BaseException): ResponseEntity<ErrorResponse> {
        val errorCode = exception.errorCode
        if (errorCode == ErrorCode.UNKNOWN_SERVER_ERROR) {
            return handleUnhandledException(request, exception)
        }

        log.warn("[BaseException] {}: {}", errorCode.code, errorCode.message, exception)
        return ResponseEntity
                .status(HttpStatus.valueOf(errorCode.status))
                .body(of(errorCode))
    }

    @ExceptionHandler(Throwable::class)
    fun handleUnhandledException(request: HttpServletRequest,
                                 exception: Throwable): ResponseEntity<ErrorResponse> {
        val dump = dumpRequest(request).append("\n ")
                .append(getStackTraceAsString(exception))

        log.error("[UnhandledException] {} \n", dump)

        return ResponseEntity
                .internalServerError()
                .body(of(ErrorCode.UNKNOWN_SERVER_ERROR))
    }

    @ExceptionHandler(value = [
        HttpMessageNotReadableException::class,
        InvalidParameterException::class,
        ServletRequestBindingException::class,
        MethodArgumentNotValidException::class,
        ConstraintViolationException::class,
        MethodArgumentTypeMismatchException::class,
        IllegalArgumentException::class
    ])
    fun handleValidateException(request: HttpServletRequest?,
                                exception: Exception?): ResponseEntity<ErrorResponse> {
        log.warn("[InvalidParameterException]", exception)
        return ResponseEntity
                .badRequest()
                .body(of(ErrorCode.INVALID_INPUT_VALUE, exception))
    }

    private fun getStackTraceAsString(throwable: Throwable): String {
        val stringWriter = StringWriter()
        throwable.printStackTrace(PrintWriter(stringWriter))
        return stringWriter.toString()
    }

    private fun dumpRequest(request: HttpServletRequest): StringBuilder {
        val dump = StringBuilder("HttpRequest Dump:")
                .append("\n  Remote Addr   : ").append(request.remoteAddr)
                .append("\n  Protocol      : ").append(request.protocol)
                .append("\n  Request Method: ").append(request.method)
                .append("\n  Request URI   : ").append(request.requestURI)
                .append("\n  Query String  : ").append(request.queryString)
                .append("\n  Parameters    : ")

        val parameterNames = request.parameterNames
        while (parameterNames.hasMoreElements()) {
            val name = parameterNames.nextElement()
            dump.append("\n    ").append(name).append('=')
            val parameterValues = request.getParameterValues(name)
            for (value in parameterValues) {
                dump.append(value)
            }
        }

        dump.append("\n  Headers       : ")
        val headerNames = request.headerNames
        while (headerNames.hasMoreElements()) {
            val name = headerNames.nextElement()
            dump.append("\n    ").append(name).append(":")
            val headerValues = request.getHeaders(name)
            while (headerValues.hasMoreElements()) {
                dump.append(headerValues.nextElement())
            }
        }

        dump.append("\n  Body       : ")
        if (request.contentType != null && request.contentType
                        .contains("application/x-www-form-urlencoded")) {
            dump.append("\n    ")
                    .append("Body is not readable for 'application/x-www-form-urlencoded' type or has been read")
        } else {
            try {
                dump.append("\n    ").append(readableToString(request.reader))
            } catch (ex: IOException) {
                dump.append("\n    ").append("NOT_READABLE")
            } catch (ex: IllegalStateException) {
                dump.append("\n    ").append("BODY_ALREADY_READ")
            }
        }

        return dump
    }

    @Throws(IOException::class)
    fun readableToString(readable: Readable): String {
        val stringBuilder = StringBuilder()
        val buffer = CharBuffer.allocate(1024)

        while (readable.read(buffer) != -1) {
            buffer.flip()
            stringBuilder.append(buffer)
            buffer.clear()
        }
        return stringBuilder.toString()
    }
}
