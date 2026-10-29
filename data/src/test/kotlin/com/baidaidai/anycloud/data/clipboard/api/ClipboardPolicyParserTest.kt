package com.baidaidai.anycloud.data.clipboard.api

import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicyBundle
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicyBundleGroup
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicyBundleRule
import com.baidaidai.anycloud.domain.clipboard.PolicyType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class ClipboardPolicyParserTest {

    @Test
    fun parseMapsRemoteGroupsAndPolicies() {
        val yamlContent = """
            policy-groups:
              - name: "🔍Bing"
                package: "com.baidaidai.rootless_store"
            policies:
              - DOMAIN-SUFFIX,xhscdn.com,🔍Bing
              - DOMAIN,mzstatic.com,🔍Bing
        """.trimIndent()

        val clipboardPolicyBundle = ClipboardPolicyParser.parse(yamlContent)

        assertEquals(
            ClipboardPolicyBundle(
                groups = listOf(
                    ClipboardPolicyBundleGroup(
                        groupName = "🔍Bing",
                        targetPackageName = "com.baidaidai.rootless_store"
                    )
                ),
                policies = listOf(
                    ClipboardPolicyBundleRule(
                        policyType = PolicyType.DOMAIN_SUFFIX,
                        policyContent = "xhscdn.com",
                        groupName = "🔍Bing"
                    ),
                    ClipboardPolicyBundleRule(
                        policyType = PolicyType.DOMAIN,
                        policyContent = "mzstatic.com",
                        groupName = "🔍Bing"
                    )
                )
            ),
            clipboardPolicyBundle
        )
    }

    @Test
    fun parseKeepsGroupsWithoutPolicies() {
        val yamlContent = """
            policy-groups:
              - name: Empty
                package: com.example.app
        """.trimIndent()

        val clipboardPolicyBundle = ClipboardPolicyParser.parse(yamlContent)

        assertEquals(1, clipboardPolicyBundle.groups.size)
        assertEquals(emptyList<ClipboardPolicyBundleRule>(), clipboardPolicyBundle.policies)
    }

    @Test
    fun parseRejectsPolicyWithUnknownGroup() {
        val yamlContent = """
            policy-groups:
              - name: Known
                package: com.example.app
            policies:
              - DOMAIN,example.com,Unknown
        """.trimIndent()

        assertThrows(IllegalArgumentException::class.java) {
            ClipboardPolicyParser.parse(yamlContent)
        }
    }

    @Test
    fun parseRejectsUnsupportedPolicyType() {
        val yamlContent = """
            policy-groups:
              - name: Known
                package: com.example.app
            policies:
              - GEOIP,CN,Known
        """.trimIndent()

        assertThrows(IllegalArgumentException::class.java) {
            ClipboardPolicyParser.parse(yamlContent)
        }
    }
}
