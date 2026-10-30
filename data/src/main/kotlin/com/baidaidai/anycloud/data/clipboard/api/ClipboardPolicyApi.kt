package com.baidaidai.anycloud.data.clipboard.api

import com.baidaidai.anycloud.core.array.formatAsMultilineString
import com.baidaidai.anycloud.domain.clipboard.ClipboardError
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.runCatching
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClipboardPolicyApi @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun fetchClipboardPolicyBundleContent(
        uri: String
    ): Result<String, ClipboardError> = runCatching {

        require(uri.isNotBlank()) {
            "Clipboard policy URI must not be empty"
        }

        val response = httpClient.get(uri)
        val yamlContent = response.bodyAsText()

        yamlContent

    }.mapError { throwable ->

        ClipboardError(
            errorMessage = "Policy download failed",
            errorCause = throwable.stackTrace.formatAsMultilineString(),
            errorCompanion = when {
                uri.isBlank() -> "Enter a subscription URL to import policies."
                throwable is IllegalArgumentException -> "The subscription URL is invalid. Check the address and try again."
                else -> "The policy file could not be downloaded. Check the URL, network connection, and server."
            }
        )

    }

}