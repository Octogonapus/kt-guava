/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isEmpty
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ImmutableSetUtilTest {

    @Test
    fun `test toImmutableSet with list`() {
        val setBefore = setOf(1, 2, 1, 3)
        val setAfter = setBefore.toImmutableSet()
        assertEquals(setBefore, setAfter)
    }

    @Test
    fun `test toImmutableSet with set`() {
        val setBefore = setOf(1, 2, 1, 3)
        val setAfter = setBefore.toImmutableSet()
        assertEquals(setBefore.toSet(), setAfter)
    }

    @Test
    fun `test toImmutableSet with array`() {
        val setBefore = arrayOf(1, 2, 1, 3)
        val setAfter = setBefore.toImmutableSet()
        assertEquals(setBefore.toSet(), setAfter)
    }

    @Test
    fun `test immutableSetOf`() {
        val usingSet = setOf(1, 2, 1, 3)
        val usingImmutableSet = immutableSetOf(1, 2, 1, 3)
        assertEquals(usingSet, usingImmutableSet)
    }

    @Test
    fun `test emptyImmutableSet`() {
        assertThat(emptyImmutableSet(), isEmpty)
    }

    @Test
    fun `test plus`() {
        val manualSet = setOf(1, 2, 1, 3, 4)
        val immutableSet = immutableSetOf(
            1,
            2,
            1
        ) + immutableSetOf(3, 4)
        assertEquals(manualSet, immutableSet)
    }

    @Test
    fun `test minus`() {
        val manualSet1 = setOf(1, 2, 3)
        val manualSet2 = setOf(2)
        val manualDifference = manualSet1.toMutableSet()
        manualDifference.removeAll(manualSet2)

        val immutableSet = immutableSetOf(
            1,
            2,
            3
        ) - immutableSetOf(2)

        assertEquals(manualDifference, immutableSet)
    }
}
