/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.klaxon

import com.beust.klaxon.Klaxon
import com.google.common.collect.ImmutableSetMultimap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.octogonapus.ktguava.collections.immutableSetMultimapOf

internal class ImmutableSetMultimapConverterTest {

    internal data class TestData(
        @ConvertImmutableSetMultimap
        val setMultimap: ImmutableSetMultimap<String, String>
    )

    private val klaxon = Klaxon().also {
        it.fieldConverter(ConvertImmutableSetMultimap::class, it.immutableSetMultimapConverter())
    }

    @Test
    fun `test converting to json`() {
        assertEquals(
            """{"setMultimap" : {"a": ["b", "c"]}}""",
            klaxon.toJsonString(
                TestData(
                    immutableSetMultimapOf("a" to "b", "a" to "b", "a" to "c")
                )
            )
        )
    }

    @Test
    fun `test converting from json`() {
        assertEquals(
            TestData(
                immutableSetMultimapOf("a" to "b", "a" to "b", "a" to "c")
            ),
            klaxon.parse<TestData>("""{"setMultimap" : {"a": ["b", "b", "c"]}}""")
        )
    }
}
