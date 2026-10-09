package com.baidaidai.regexpstorm

import com.baidaidai.anycloud.core.array.formatAsMultilineString
import com.baidaidai.regexpstorm.domain.RegexpError
import com.github.michaelbull.result.*
import java.util.regex.Pattern

class RegexpStorm(
    private val template: String
) {

    fun resolveDomainSuffix(): Result<String, RegexpError> =
        runCatching {
            Pattern.quote(template)
        }
        .mapError { throwable ->
            RegexpError(
                errorMessage = throwable.message ?: "Failed To Regex",
                errorCause = throwable.stackTrace.formatAsMultilineString(),
                errorCompanion = "Can't resolve domain suffix"
            )
        }

    fun resolveDomain():Result<String, RegexpError> =
        runCatching {
            Pattern.quote(template)
        }
        .mapError { throwable ->
            RegexpError(
                errorMessage = throwable.message ?: "Failed To Regex",
                errorCause = throwable.stackTrace.formatAsMultilineString(),
                errorCompanion = "Can't resolve domain"
            )
        }

}