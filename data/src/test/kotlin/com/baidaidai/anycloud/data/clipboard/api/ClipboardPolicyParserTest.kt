package com.baidaidai.anycloud.data.clipboard.api

import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicyBundle
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicyBundleGroup
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicyBundleRule
import com.baidaidai.anycloud.domain.clipboard.PolicyType
import com.github.michaelbull.result.fold
import org.junit.Assert.assertEquals
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

        val clipboardPolicyBundle = ClipboardPolicyParser.parse(yamlContent).fold(
            success = { it },
            failure = { throw AssertionError(it.errorMessage) }
        )

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

        val clipboardPolicyBundle = ClipboardPolicyParser.parse(yamlContent).fold(
            success = { it },
            failure = { throw AssertionError(it.errorMessage) }
        )

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

        val clipboardError = ClipboardPolicyParser.parse(yamlContent).fold(
            success = { throw AssertionError("Expected parsing to fail") },
            failure = { it }
        )

        assertEquals("Invalid policy file", clipboardError.errorMessage)
        assertEquals("Policy group does not exist: Unknown", clipboardError.errorCompanion)
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

        val clipboardError = ClipboardPolicyParser.parse(yamlContent).fold(
            success = { throw AssertionError("Expected parsing to fail") },
            failure = { it }
        )

        assertEquals("Invalid policy file", clipboardError.errorMessage)
        assertEquals("Unsupported policy type: GEOIP", clipboardError.errorCompanion)
    }
}
