package com.fancychild.bapsanghead.exception

data class BaseException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)
