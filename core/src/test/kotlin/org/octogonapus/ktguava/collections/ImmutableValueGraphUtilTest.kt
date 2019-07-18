/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.google.common.graph.MutableValueGraph
import com.google.common.graph.ValueGraph
import com.google.common.graph.ValueGraphBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

internal class ImmutableValueGraphUtilTest {

    @Test
    fun `test toImmutableValueGraph`() {
        val graph = makeMockValueGraph()
        val immutableGraph = graph.toImmutableValueGraph()
        assertValueGraphsAreEqual(graph, immutableGraph)
    }

    @Test
    fun `test mapNodes with mutable receiver`() {
        val graphMapped = makeMockValueGraph(listOf(0, 1)).mapNodes { it + 1 }
        val expected = makeMockValueGraph(listOf(1, 2))
        assertValueGraphsAreEqual(expected, graphMapped)
    }

    @Test
    fun `test mapNodes with immutable receiver`() {
        val graphMapped = makeMockValueGraph(listOf(0, 1))
            .toImmutableValueGraph()
            .mapNodes { it + 1 }
        val expected = makeMockValueGraph(listOf(1, 2))
        assertValueGraphsAreEqual(expected, graphMapped)
    }

    @Suppress("UnstableApiUsage")
    @Test
    fun `obeys self loops false`() {
        val expected = ValueGraphBuilder.directed().allowsSelfLoops(false).build<Int, Int>()
        val actual = expected.mapNodes { 0 }
        assertEquals(expected.allowsSelfLoops(), actual.allowsSelfLoops())
    }

    @Suppress("UnstableApiUsage")
    @Test
    fun `obeys self loops true`() {
        val expected = ValueGraphBuilder.directed().allowsSelfLoops(true).build<Int, Int>()
        val actual = expected.mapNodes { 0 }
        assertEquals(expected.allowsSelfLoops(), actual.allowsSelfLoops())
    }

    @Suppress("UnstableApiUsage")
    private fun makeMockValueGraph(
        nodeValues: List<Int> = listOf(0, 1),
        edgeValues: List<Int> = listOf(1)
    ): MutableValueGraph<Int, Int> =
        ValueGraphBuilder.undirected().build<Int, Int>().apply {
            nodeValues.forEach { addNode(it) }
            nodeValues.chunked(2)
                .map { it[0] to it[1] }
                .zip(edgeValues)
                .forEach { (nodes, edge) ->
                    putEdgeValue(nodes.first, nodes.second, edge)
                }
        }

    @Suppress("UnstableApiUsage")
    private fun <N : Any, V : Any> assertValueGraphsAreEqual(
        expected: ValueGraph<N, V>,
        actual: ValueGraph<N, V>
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
