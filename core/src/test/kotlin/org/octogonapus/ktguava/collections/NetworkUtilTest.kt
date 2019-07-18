/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
@file:Suppress("UnstableApiUsage")

package org.octogonapus.ktguava.collections

import com.google.common.graph.EndpointPair
import com.google.common.graph.MutableNetwork
import com.google.common.graph.NetworkBuilder
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class NetworkUtilTest {

    @Test
    fun `test in nodes and edges`() {
        val network = NetworkBuilder.directed()
            .build<String, Int>()
            .apply {
                edges(
                    "A" to "B",
                    "A" to "C",
                    "D" to "A",
                    "E" to "A"
                )
            }

        assertEquals(
            setOf(
                EndpointPair.ordered("D", "A") to 2,
                EndpointPair.ordered("E", "A") to 3
            ), network.inNodesAndEdges("A")
        )
    }

    @Test
    fun `test in nodes`() {
        val network = NetworkBuilder.directed()
            .build<String, Int>()
            .apply {
                edges(
                    "A" to "B",
                    "A" to "C",
                    "D" to "A",
                    "E" to "A"
                )
            }

        assertEquals(setOf("D", "E"), network.inNodes("A"))
    }

    @Test
    fun `test out nodes and edges`() {
        val network = NetworkBuilder.directed()
            .build<String, Int>()
            .apply {
                edges(
                    "A" to "B",
                    "A" to "C",
                    "D" to "A"
                )
            }

        assertEquals(
            setOf(
                EndpointPair.ordered("A", "B") to 0,
                EndpointPair.ordered("A", "C") to 1
            ), network.outNodesAndEdges("A")
        )
    }

    @Test
    fun `test out nodes`() {
        val network = NetworkBuilder.directed()
            .build<String, Int>()
            .apply {
                edges(
                    "A" to "B",
                    "A" to "C",
                    "D" to "A"
                )
            }

        assertEquals(setOf("B", "C"), network.outNodes("A"))
    }

    private fun MutableNetwork<String, Int>.edges(vararg pairs: Pair<String, String>) =
        pairs.forEachIndexed { index, pair ->
            addEdge(pair.first, pair.second, index)
        }
}
