/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.google.common.graph.MutableNetwork
import com.google.common.graph.Network
import com.google.common.graph.NetworkBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals

internal class ImmutableNetworkUtilTest {

    @Test
    fun `test toImmutableNetwork with undirected network`() {
        val network = makeUndirectedMockNetwork()
        val immutableNetwork = network.toImmutableNetwork()
        assertNetworksAreEqual(network, immutableNetwork)
    }

    @Test
    fun `test toImmutableNetwork with directed network`() {
        val network = makeDirectedMockNetwork()
        val immutableNetwork = network.toImmutableNetwork()
        assertNetworksAreEqual(network, immutableNetwork)
    }

    @Test
    fun `test mapNodes with mutable receiver and undirected network`() {
        val graphMapped = makeUndirectedMockNetwork(listOf(0, 1)).mapNodes { it + 1 }
        val expected = makeUndirectedMockNetwork(listOf(1, 2))
        assertNetworksAreEqual(expected, graphMapped)
    }

    @Test
    fun `test mapNodes with mutable receiver and directed network`() {
        val graphMapped = makeDirectedMockNetwork(listOf(0, 1)).mapNodes { it + 1 }
        val expected = makeDirectedMockNetwork(listOf(1, 2))
        assertNetworksAreEqual(expected, graphMapped)
    }

    @Test
    fun `test mapNodes with immutable receiver and undirected network`() {
        val graphMapped = makeUndirectedMockNetwork(listOf(0, 1))
            .toImmutableNetwork()
            .mapNodes { it + 1 }
        val expected = makeUndirectedMockNetwork(listOf(1, 2))
        assertNetworksAreEqual(expected, graphMapped)
    }

    @Test
    fun `test mapNodes with immutable receiver and directed network`() {
        val graphMapped = makeDirectedMockNetwork(listOf(0, 1))
            .toImmutableNetwork()
            .mapNodes { it + 1 }
        val expected = makeDirectedMockNetwork(listOf(1, 2))
        assertNetworksAreEqual(expected, graphMapped)
    }

    @Suppress("UnstableApiUsage")
    @Test
    fun `obeys parallel edges false`() {
        val expected = NetworkBuilder.directed().allowsParallelEdges(false).build<Int, Int>()
        val actual = expected.mapNodes { 0 }
        assertEquals(expected.allowsParallelEdges(), actual.allowsParallelEdges())
    }

    @Suppress("UnstableApiUsage")
    @Test
    fun `obeys parallel edges true`() {
        val expected = NetworkBuilder.directed().allowsParallelEdges(true).build<Int, Int>()
        val actual = expected.mapNodes { 0 }
        assertEquals(expected.allowsParallelEdges(), actual.allowsParallelEdges())
    }

    @Suppress("UnstableApiUsage")
    @Test
    fun `obeys self loops false`() {
        val expected = NetworkBuilder.directed().allowsSelfLoops(false).build<Int, Int>()
        val actual = expected.mapNodes { 0 }
        assertEquals(expected.allowsSelfLoops(), actual.allowsSelfLoops())
    }

    @Suppress("UnstableApiUsage")
    @Test
    fun `obeys self loops true`() {
        val expected = NetworkBuilder.directed().allowsSelfLoops(true).build<Int, Int>()
        val actual = expected.mapNodes { 0 }
        assertEquals(expected.allowsSelfLoops(), actual.allowsSelfLoops())
    }

    @Suppress("UnstableApiUsage")
    private fun makeUndirectedMockNetwork(
        nodeValues: List<Int> = listOf(0, 1),
        edgeValues: List<Int> = listOf(1)
    ): MutableNetwork<Int, Int> =
        NetworkBuilder.undirected().build<Int, Int>().apply {
            nodeValues.forEach { addNode(it) }
            nodeValues.chunked(2)
                .map { it[0] to it[1] }
                .zip(edgeValues)
                .forEach { (nodes, edge) ->
                    addEdge(nodes.first, nodes.second, edge)
                }
        }

    @Suppress("UnstableApiUsage")
    private fun makeDirectedMockNetwork(
        nodeValues: List<Int> = listOf(0, 1),
        edgeValues: List<Int> = listOf(1)
    ): MutableNetwork<Int, Int> =
        NetworkBuilder.directed().build<Int, Int>().apply {
            nodeValues.forEach { addNode(it) }
            nodeValues.chunked(2)
                .map { it[0] to it[1] }
                .zip(edgeValues)
                .forEach { (nodes, edge) ->
                    addEdge(nodes.first, nodes.second, edge)
                }
        }

    @Suppress("UnstableApiUsage")
    private fun <N : Any, V : Any> assertNetworksAreEqual(
        expected: Network<N, V>,
        actual: Network<N, V>
    ) {
        assertAll(
            { assertEquals(expected.nodes(), actual.nodes()) },
            { assertEquals(expected.edges(), actual.edges()) },
            {
                assertEquals(
                    expected.nodes().flatMap { expected.incidentEdges(it) }.toSet(),
                    actual.nodes().flatMap { actual.incidentEdges(it) }.toSet()
                )
            },
            {
                assertEquals(
                    expected.edges().flatMap {
                        expected.incidentNodes(it).let { listOf(it.nodeU(), it.nodeV()) }
                    }.toSet(),
                    actual.edges().flatMap {
                        actual.incidentNodes(it).let { listOf(it.nodeU(), it.nodeV()) }
                    }.toSet()
                )
            }
        )
    }
}
