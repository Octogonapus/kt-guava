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

internal class ImmutableListUtilTest {

    @Test
    fun `test toImmutableList with list`() {
        val listBefore = listOf(1, 2, 3)
        val listAfter = listBefore.toImmutableList()
        assertEquals(listBefore, listAfter)
    }

    @Test
    fun `test toImmutableList with set`() {
        val setBefore = setOf(1, 2, 3)
        val listAfter = setBefore.toImmutableList()
        assertEquals(setBefore.toList(), listAfter)
    }

    @Test
    fun `test toImmutableList with array`() {
        val setBefore = arrayOf(1, 2, 3)
        val listAfter = setBefore.toImmutableList()
        assertEquals(setBefore.toList(), listAfter)
    }

    @Test
    fun `test immutableListOf`() {
        val usingList = listOf(1, 2, 3)
        val usingImmutableList = immutableListOf(1, 2, 3)
        assertEquals(usingList, usingImmutableList)
    }

    @Test
    fun `test emptyImmutableList`() {
        assertThat(emptyImmutableList(), isEmpty)
    }

    @Test
    fun `test plus`() {
        val manualList = listOf(1, 2, 3, 4)
        val immutableList = immutableListOf(
            1,
            2
        ) + immutableListOf(3, 4)
        assertEquals(manualList, immutableList)
    }

    @Test
    fun `test minus`() {
        val manualList1 = listOf(1, 2, 3)
        val manualList2 = listOf(2)
        val manualDifference = manualList1.toMutableList()
        manualDifference.removeAll(manualList2)

        val immutableList = immutableListOf(
            1,
            2,
            3
        ) - immutableListOf(2)

        assertEquals(manualDifference, immutableList)
    }
}
