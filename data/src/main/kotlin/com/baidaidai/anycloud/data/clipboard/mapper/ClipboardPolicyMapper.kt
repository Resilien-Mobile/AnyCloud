package com.baidaidai.anycloud.data.clipboard.mapper

import com.baidaidai.anycloud.data.clipboard.database.PolicyEntity
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import com.baidaidai.anycloud.domain.clipboard.PolicyType

object ClipboardPolicyMapper {

    internal fun PolicyEntity.toClipboardPolicy(): ClipboardPolicy {

        // 缓解转成纯 String 字符串后
        // 无法进行类型比对的问题
        val resolvePolicyType = PolicyType
            .entries
            .firstOrNull { policyType ->
                policyType.name == this.policyType
            } ?: PolicyType.DOMAIN

        val clipboardPolicy = ClipboardPolicy(
            unixTimeStamp = unixTimeStamp,
            policyType = resolvePolicyType,
            policyContent = policyContent,
            policyGroup = policyGroup
        )

        return clipboardPolicy
    }

    internal fun ClipboardPolicy.toPolicyEntity(): PolicyEntity {
        val policyEntity = PolicyEntity(
            unixTimeStamp = unixTimeStamp,
            policyType = policyType.name,
            policyContent = policyContent,
            policyGroup = policyGroup
        )

        return policyEntity
    }

}
