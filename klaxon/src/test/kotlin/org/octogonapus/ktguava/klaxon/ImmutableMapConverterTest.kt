/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.klaxon

import com.beust.klaxon.Klaxon
import com.google.common.collect.ImmutableMap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.octogonapus.ktguava.collections.immutableMapOf

internal class ImmutableMapConverterTest {

    internal data class TestData(
        @ConvertImmutableMap
        val map: ImmutableMap<String, String>
    )

    private val klaxon = Klaxon().also {
        it.fieldConverter(ConvertImmutableMap::class, it.immutableMapConverter())
    }

    @Test
    fun `test converting to json`() {
        assertEquals(
            """{"map" : {"a": "b"}}""",
            klaxon.toJsonString(
                TestData(
                    immutableMapOf("a" to "b")
                )
            )
        )
    }

    @Test
    fun `test converting from json`() {
        assertEquals(
            TestData(immutableMapOf("a" to "b")),
            klaxon.parse<TestData>("""{"map" : {"a": "b"}}""")
        )
    }
}
