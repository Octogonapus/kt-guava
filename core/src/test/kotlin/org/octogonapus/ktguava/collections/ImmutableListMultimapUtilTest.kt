/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.google.common.collect.ArrayListMultimap
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isEmpty
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

internal class ImmutableListMultimapUtilTest {

    @Test
    fun `test toImmutableListMultimap with multimap`() {
        val multimap = ArrayListMultimap.create<Int, Int>()
        multimap.put(0, 1)

        val immutableMultimap = multimap.toImmutableListMultimap()

        assertEquals(multimap.asMap(), immutableMultimap.asMap())
    }

    @Test
    fun `test toImmutableListMultimap with iterable`() {
        val multimap = immutableListOf(
            0 to immutableListOf(1)
        )

        val immutableMultimap = multimap.toImmutableListMultimap()

        assertAll(
            {
                assertIterableEquals(
                    mapOf(0 to listOf(1)).keys,
                    immutableMultimap.asMap().toMap().keys
                )
            },
            {
                assertIterableEquals(
                    mapOf(0 to listOf(1)).values,
                    immutableMultimap.asMap().toMap().values
                )
            }
        )
    }

    @Test
    fun `test immutableListMultimapOf`() {
        val multimap = ArrayListMultimap.create<Int, Int>()
        multimap.put(0, 1)
        multimap.put(1, 2)

        val immutableMultimap =
            immutableListMultimapOf(0 to 1, 1 to 2)

        assertEquals(multimap.asMap(), immutableMultimap.asMap())
    }

    @Test
    fun `test emptyImmutableListMultimap`() {
        assertThat(emptyImmutableListMultimap<Int, Int>().entries(), isEmpty)
    }

    @Test
    fun `test plus with multimap`() {
        val multimap = ArrayListMultimap.create<Int, Int>()
        multimap.put(0, 1)
        multimap.put(1, 2)

        val immutableMultimap = immutableListMultimapOf(0 to 1) + immutableListMultimapOf(
            1 to 2
        )

        assertEquals(multimap.asMap(), immutableMultimap.asMap())
    }

    @Test
    fun `test plus with iterable of entries`() {
        val multimap = ArrayListMultimap.create<Int, Int>()
        multimap.put(0, 1)
        multimap.put(1, 2)

        val immutableMultimap = immutableListMultimapOf(0 to 1) + mapOf(1 to 2).entries

        assertEquals(multimap.asMap(), immutableMultimap.asMap())
    }
}
