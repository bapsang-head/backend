package com.fancychild.bapsanghead.domain.auth.util

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.RestClient
import java.nio.charset.StandardCharsets

open class OAuth2Manager {

    private val log = LoggerFactory.getLogger(OAuth2Manager::class.java)

    fun createRestClient(baseUrl: String): RestClient {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultStatusHandler(
                        { obj: HttpStatusCode ->
                            obj.is4xxClientError
                        },
                        { _, response: ClientHttpResponse ->
                            log.error("Client Error Code={}", response.statusCode)
                            log.error("Client Error Message={}",
                                    String(response.body.readAllBytes()))
                        }
                )
                .defaultStatusHandler(
                        { obj: HttpStatusCode ->
                            obj.is5xxServerError
                        },
                        { _, response: ClientHttpResponse ->
                            log.error("Server Error Code={}", response.statusCode)
                            log.error("Server Error Message={}",
                                    String(response.body.readAllBytes()))
                        }
                )
                .build()
    }

    companion object {
        val MEDIA_TYPE: String = MediaType(MediaType.APPLICATION_FORM_URLENCODED,
                StandardCharsets.UTF_8).toString()
    }
}
