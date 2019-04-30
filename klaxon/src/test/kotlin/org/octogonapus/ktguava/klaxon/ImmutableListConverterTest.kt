/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.klaxon

import com.beust.klaxon.Klaxon
import com.google.common.collect.ImmutableList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.octogonapus.ktguava.collections.immutableListOf

internal class ImmutableListConverterTest {

    internal data class TestData(
        @ConvertImmutableList
        val list: ImmutableList<String>
    )

    private val klaxon = Klaxon().also {
        it.fieldConverter(ConvertImmutableList::class, it.immutableListConverter())
    }

    @Test
    fun `test converting to json`() {
        assertEquals(
            """{"list" : ["a", "b"]}""",
            klaxon.toJsonString(
                TestData(
                    immutableListOf("a", "b")
                )
            )
        )
    }

    @Test
    fun `test converting from json`() {
        assertEquals(
            TestData(
                immutableListOf(
                    "a",
                    "b"
                )
            ),
            klaxon.parse<TestData>("""{"list" : ["a", "b"]}""")
        )
    }
}
