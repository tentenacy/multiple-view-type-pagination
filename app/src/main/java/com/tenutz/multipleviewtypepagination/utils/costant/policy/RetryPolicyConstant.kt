package com.tenutz.multipleviewtypepagination.utils.costant.policy

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


typealias RETRY_PREDICATE = (Throwable) -> Boolean

object RetryPolicyConstant {

    const val MAX_RETRIES = 3L
    const val DEFAULT_INTERVAL = 1L

    val TIMEOUT: RETRY_PREDICATE = { it is SocketTimeoutException }
    val NETWORK: RETRY_PREDICATE = { it is IOException }
    val SERVICE_UNAVAILABLE: RETRY_PREDICATE = { it is HttpException && it.code() == 503 }
}