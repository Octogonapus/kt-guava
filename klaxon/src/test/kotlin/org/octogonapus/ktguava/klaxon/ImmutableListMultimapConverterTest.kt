/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.klaxon

import com.beust.klaxon.Klaxon
import com.google.common.collect.ImmutableListMultimap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.octogonapus.ktguava.collections.immutableListMultimapOf

internal class ImmutableListMultimapConverterTest {

    internal data class TestData(
        @ConvertImmutableListMultimap
        val listMultimap: ImmutableListMultimap<String, String>
    )

    private val klaxon = Klaxon().also {
        it.fieldConverter(ConvertImmutableListMultimap::class, it.immutableListMultimapConverter())
    }

    @Test
    fun `test converting to json`() {
        assertEquals(
            """{"listMultimap" : {"a": ["b", "b", "c"]}}""",
            klaxon.toJsonString(
                TestData(
                    immutableListMultimapOf("a" to "b", "a" to "b", "a" to "c")
                )
            )
        )
    }

    @Test
    fun `test converting from json`() {
        assertEquals(
            TestData(
                immutableListMultimapOf("a" to "b", "a" to "b", "a" to "c")
            ),
            klaxon.parse<TestData>("""{"listMultimap" : {"a": ["b", "b", "c"]}}""")
        )
    }
}
