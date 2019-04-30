/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.klaxon

import com.beust.klaxon.Klaxon
import com.google.common.collect.ImmutableSet
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.octogonapus.ktguava.collections.immutableSetOf

internal class ImmutableSetConverterTest {

    internal data class TestData(
        @ConvertImmutableSet
        val set: ImmutableSet<String>
    )

    private val klaxon = Klaxon().also {
        it.fieldConverter(ConvertImmutableSet::class, it.immutableSetConverter())
    }

    @Test
    fun `test converting to json`() {
        assertEquals(
            """{"set" : ["a", "b"]}""",
            klaxon.toJsonString(
                TestData(
                    immutableSetOf("a", "b", "a")
                )
            )
        )
    }

    @Test
    fun `test converting from json`() {
        assertEquals(
            TestData(
                immutableSetOf(
                    "a",
                    "b"
                )
            ),
            klaxon.parse<TestData>("""{"set" : ["a", "b", "a"]}""")
        )
    }
}
