package com.baidaidai.anycloud.data.clipboard.api

import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicyBundle
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClipboardPolicyApi @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun fetchClipboardPolicyBundle(
        uri: String
    ): ClipboardPolicyBundle {
        require(uri.isNotBlank()) {
            "Clipboard policy URI must not be empty"
        }

        val response = httpClient.get(uri)
        val yamlContent = response.bodyAsText()
        val clipboardPolicyBundle = ClipboardPolicyParser.parse(yamlContent)

        return clipboardPolicyBundle
    }

}