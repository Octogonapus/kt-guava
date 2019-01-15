/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.guavautil.collections

import com.google.common.collect.HashMultimap
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isEmpty
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ImmutableSetMultimapUtilTest {

    @Test
    fun `test toImmutableSetMultimap`() {
        val multimap = HashMultimap.create<Int, Int>()
        multimap.put(0, 1)

        val immutableMultimap = multimap.toImmutableSetMultimap()

        assertEquals(multimap.asMap(), immutableMultimap.asMap())
    }

    @Test
    fun `test immutableSetMultimapOf`() {
        val multimap = HashMultimap.create<Int, Int>()
        multimap.put(0, 1)
        multimap.put(1, 2)

        val immutableMultimap = immutableSetMultimapOf(0 to 1, 1 to 2)

        assertEquals(multimap.asMap(), immutableMultimap.asMap())
    }

    @Test
    fun `test emptyImmutableSetMultimap`() {
        assertThat(emptyImmutableSetMultimap<Int, Int>().entries(), isEmpty)
    }

    @Test
    fun `test plus with multimap`() {
        val multimap = HashMultimap.create<Int, Int>()
        multimap.put(0, 1)
        multimap.put(1, 2)

        val immutableMultimap = immutableSetMultimapOf(0 to 1) + immutableSetMultimapOf(1 to 2)

        assertEquals(multimap.asMap(), immutableMultimap.asMap())
    }

    @Test
    fun `test plus with iterable of entries`() {
        val multimap = HashMultimap.create<Int, Int>()
        multimap.put(0, 1)
        multimap.put(1, 2)

        val immutableMultimap = immutableSetMultimapOf(0 to 1) + mapOf(1 to 2).entries

        assertEquals(multimap.asMap(), immutableMultimap.asMap())
    }
}
