/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.google.common.graph.MutableValueGraph
import com.google.common.graph.ValueGraphBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

internal class ImmutableValueGraphUtilTest {

    @Test
    fun `test toImmutableValueGraph`() {
        val graph = makeMockValueGraph()
        val immutableGraph = graph.toImmutableValueGraph()

        assertAll(
            { assertEquals(graph.nodes(), immutableGraph.nodes()) },
            { assertEquals(graph.edges(), immutableGraph.edges()) }
        )
    }

    @Test
    fun `test mapNodes with mutable receiver`() {
        val graph = makeMockValueGraph()
        val graphMapped = graph.mapNodes { it + 1 }

        assertEquals(graphMapped.nodes(), setOf(1, 2))
    }

    @Test
    fun `test mapNodes with immutable receiver`() {
        val graph = makeMockValueGraph()
        val graphMapped = graph.toImmutableValueGraph().mapNodes { it + 1 }

        assertEquals(graphMapped.nodes(), setOf(1, 2))
    }

    @Suppress("UnstableApiUsage")
    private fun makeMockValueGraph(): MutableValueGraph<Int, Int> =
        ValueGraphBuilder.undirected().build<Int, Int>().apply {
            addNode(0)
            addNode(1)
            putEdgeValue(0, 1, 1)
        }
}
