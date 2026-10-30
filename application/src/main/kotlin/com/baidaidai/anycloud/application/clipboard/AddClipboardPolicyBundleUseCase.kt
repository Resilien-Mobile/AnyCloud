package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.api.ClipboardPolicyApi
import com.baidaidai.anycloud.data.clipboard.api.ClipboardPolicyParser
import com.baidaidai.anycloud.data.clipboard.repository.ClipboardPolicyBundleRepositoryImpl
import com.baidaidai.anycloud.domain.clipboard.ClipboardError
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse
import javax.inject.Inject

class AddClipboardPolicyBundleUseCase @Inject constructor(
    private val clipboardPolicyApi: ClipboardPolicyApi,
    private val clipboardPolicyBundleRepositoryImpl: ClipboardPolicyBundleRepositoryImpl
) {

    suspend operator fun invoke(
        uri: String
    ): Result<Unit, ClipboardError> {

        val clipboardPolicyBundleContent = clipboardPolicyApi
            .fetchClipboardPolicyBundleContent(uri)
            .getOrElse { return Err(it) }

        val clipboardPolicyBundle = ClipboardPolicyParser
            .parse(clipboardPolicyBundleContent)
            .getOrElse { return Err(it) }

        clipboardPolicyBundleRepositoryImpl
            .addClipboardPolicyBundle(clipboardPolicyBundle)
            .getOrElse { return Err(it) }

        return Ok(Unit)

    }
}