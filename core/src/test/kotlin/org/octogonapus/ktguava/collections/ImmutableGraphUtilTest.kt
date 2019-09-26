/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder
import com.google.common.graph.MutableGraph
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

internal class ImmutableGraphUtilTest {

    @Test
    fun `test toImmutableGraph`() {
        val graph = makeMockGraph()
        val immutableGraph = graph.toImmutableGraph()
        assertGraphsAreEqual(graph, immutableGraph)
    }

    @Test
    fun `test mapNodes with mutable receiver`() {
        val graphMapped = makeMockGraph(listOf(0, 1)).mapNodes { it + 1 }
        val expected = makeMockGraph(listOf(1, 2))
        assertGraphsAreEqual(expected, graphMapped)
    }

    @Test
    fun `test mapNodes with immutable receiver`() {
        val graphMapped = makeMockGraph(listOf(0, 1))
            .toImmutableGraph()
            .mapNodes { it + 1 }
        val expected = makeMockGraph(listOf(1, 2))
        assertGraphsAreEqual(expected, graphMapped)
    }

    @Suppress("UnstableApiUsage")
    @Test
    fun `obeys self loops false`() {
        val expected = GraphBuilder.directed().allowsSelfLoops(false).build<Int>()
        val actual = expected.mapNodes { 0 }
        assertEquals(expected.allowsSelfLoops(), actual.allowsSelfLoops())
    }

    @Suppress("UnstableApiUsage")
    @Test
    fun `obeys self loops true`() {
        val expected = GraphBuilder.directed().allowsSelfLoops(true).build<Int>()
        val actual = expected.mapNodes { 0 }
        assertEquals(expected.allowsSelfLoops(), actual.allowsSelfLoops())
    }

    @Suppress("UnstableApiUsage")
    private fun makeMockGraph(
        nodeValues: List<Int> = listOf(0, 1)
    ): MutableGraph<Int> =
        GraphBuilder.undirected().build<Int>().apply {
            nodeValues.forEach { addNode(it) }
            nodeValues.chunked(2)
                .map { it[0] to it[1] }
                .forEach { (nodeU, nodeV) ->
                    putEdge(nodeU, nodeV)
                }
        }

    @Suppress("UnstableApiUsage")
    private fun <N : Any> assertGraphsAreEqual(
        expected: Graph<N>,
        actual: Graph<N>
    ) {
        assertAll(
            { assertEquals(expected.nodes(), actual.nodes()) },
            { assertEquals(expected.edges(), actual.edges()) },
            {
                assertEquals(
                    expected.nodes().flatMap { expected.incidentEdges(it) }.toSet(),
                    actual.nodes().flatMap { actual.incidentEdges(it) }.toSet()
                )
            }
        )
    }
}
