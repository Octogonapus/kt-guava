/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.guavautil.collections

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.isEmpty
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ImmutableMapUtilTest {

    @Test
    fun `test toImmutableMap with map`() {
        val manualMap = mapOf(1 to 2, 3 to 4)
        val immutableMap = manualMap.toImmutableMap()
        assertEquals(manualMap, immutableMap)
    }

    @Test
    fun `test toImmutableMap with iterable of pairs`() {
        val manualMap = mapOf(1 to 2, 3 to 4)
        val immutableMap = listOf(1 to 2, 3 to 4).toImmutableMap()
        assertEquals(manualMap, immutableMap)
    }

    @Test
    fun `test immutableMapOf`() {
        val manualMap = mapOf(1 to 2, 3 to 4)
        val immutableMap = immutableMapOf(1 to 2, 3 to 4)
        assertEquals(manualMap, immutableMap)
    }

    @Test
    fun `test emptyImmutableMap`() {
        assertThat(emptyImmutableMap<Int, Int>().entries, isEmpty)
    }
}
