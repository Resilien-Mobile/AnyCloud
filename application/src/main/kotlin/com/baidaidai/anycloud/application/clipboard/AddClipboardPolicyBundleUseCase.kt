package com.baidaidai.anycloud.application.clipboard

import com.baidaidai.anycloud.data.clipboard.api.ClipboardPolicyApi
import com.baidaidai.anycloud.data.clipboard.repository.ClipboardPolicyBundleRepositoryImpl
import javax.inject.Inject

class AddClipboardPolicyBundleUseCase @Inject constructor(
    private val clipboardPolicyApi: ClipboardPolicyApi,
    private val clipboardPolicyBundleRepositoryImpl: ClipboardPolicyBundleRepositoryImpl
) {

    suspend operator fun invoke(
        uri: String
    ) {
        val clipboardPolicyBundle = clipboardPolicyApi.fetchClipboardPolicyBundle(uri)

        clipboardPolicyBundleRepositoryImpl.addClipboardPolicyBundle(clipboardPolicyBundle)
    }
}