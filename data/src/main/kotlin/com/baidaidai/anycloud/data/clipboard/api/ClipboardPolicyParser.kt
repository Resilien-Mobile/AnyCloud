package com.baidaidai.anycloud.data.clipboard.api

import com.baidaidai.anycloud.data.clipboard.api.dto.ClipboardPolicyBundleDto
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicyBundle
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicyBundleGroup
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicyBundleRule
import com.baidaidai.anycloud.domain.clipboard.PolicyType
import com.charleskorn.kaml.Yaml

object ClipboardPolicyParser {

    fun parse(
        yamlContent: String
    ): ClipboardPolicyBundle {

        val clipboardPolicyBundleDto = Yaml.default.decodeFromString(
            ClipboardPolicyBundleDto.serializer(),
            yamlContent
        )

        val clipboardPolicyGroupList = clipboardPolicyBundleDto.policyGroups.map { groupDto ->
            val groupName = groupDto.name.trim()
            val targetPackageName = groupDto.targetPackageName.trim()

            require(groupName.isNotEmpty()) {
                "Policy group name must not be empty"
            }
            require(targetPackageName.isNotEmpty()) {
                "Policy group package must not be empty: $groupName"
            }

            ClipboardPolicyBundleGroup(
                groupName = groupName,
                targetPackageName = targetPackageName
            )
        }

        val groupNameSet = clipboardPolicyGroupList.map { group ->
            group.groupName
        }.toSet()

        require(groupNameSet.size == clipboardPolicyGroupList.size) {
            "Policy group names must be unique"
        }

        val clipboardPolicyBundleRuleList = clipboardPolicyBundleDto.policies
            .map(String::trim)
            .filter(String::isNotEmpty)
            .map { policyLine ->
                parsePolicyLine(policyLine)
            }
            .distinct()

        clipboardPolicyBundleRuleList.forEach { policyRule ->
            require(policyRule.groupName in groupNameSet) {
                "Policy group does not exist: ${policyRule.groupName}"
            }
        }

        val clipboardPolicyBundle = ClipboardPolicyBundle(
            groups = clipboardPolicyGroupList,
            policies = clipboardPolicyBundleRuleList
        )

        return clipboardPolicyBundle

    }

    private fun parsePolicyLine(
        policyLine: String
    ): ClipboardPolicyBundleRule {

        val policyFieldList = policyLine.split(',', limit = 4).map(String::trim)

        require(policyFieldList.size == 3) {
            "Policy must contain exactly three comma-separated fields: $policyLine"
        }

        val policyType = when (policyFieldList[0]) {
            "DOMAIN" -> PolicyType.DOMAIN
            "DOMAIN-SUFFIX" -> PolicyType.DOMAIN_SUFFIX
            "IP-CIDR" -> PolicyType.IP_CIDR
            else -> throw IllegalArgumentException(
                "Unsupported policy type: ${policyFieldList[0]}"
            )
        }

        val policyContent = policyFieldList[1]
        val groupName = policyFieldList[2]

        require(policyContent.isNotEmpty()) {
            "Policy content must not be empty: $policyLine"
        }
        require(groupName.isNotEmpty()) {
            "Policy group name must not be empty: $policyLine"
        }

        val clipboardPolicyBundleRule = ClipboardPolicyBundleRule(
            policyType = policyType,
            policyContent = policyContent,
            groupName = groupName
        )

        return clipboardPolicyBundleRule

    }
}
