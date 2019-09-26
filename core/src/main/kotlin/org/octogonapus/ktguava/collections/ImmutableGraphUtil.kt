/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder
import com.google.common.graph.ImmutableGraph
import com.google.common.graph.MutableGraph
import com.google.common.graph.ValueGraph

@Suppress("UnstableApiUsage")
fun <N : Any> Graph<N>.toImmutableGraph(): ImmutableGraph<N> = ImmutableGraph.copyOf(this)

/**
 * Maps the nodes of the [Graph].
 *
 * @param transform A mapping function applied to the nodes.
 * @return A graph with mapped nodes.
 */
@Suppress("UnstableApiUsage")
fun <N : Any, R : Any> Graph<out N>.mapNodes(
    transform: (N) -> R
): Graph<R> = mapGraphNodes(transform)

/**
 * Maps the nodes of the [ValueGraph].
 *
 * @param transform A mapping function applied to the nodes.
 * @return A graph with mapped nodes.
 */
@Suppress("UnstableApiUsage")
fun <N : Any, R : Any> ImmutableGraph<out N>.mapNodes(
    transform: (N) -> R
): ImmutableGraph<R> = mapGraphNodes(transform).toImmutableGraph()

@Suppress("UnstableApiUsage", "MapGetWithNotNullAssertionOperator")
private fun <N : Any, R : Any> Graph<N>.mapGraphNodes(
    transform: (N) -> R
): MutableGraph<R> {
    val nodes = nodes()
    val edges = edges()
    val builder = if (isDirected) GraphBuilder.directed() else GraphBuilder.undirected()
    val mutableGraph = builder.expectedNodeCount(nodes.size)
        .allowsSelfLoops(allowsSelfLoops())
        .build<R>()

    val nodesRemapped = nodes.map { it to transform(it) }.toMap()
    nodesRemapped.values.forEach { mutableGraph.addNode(it) }

    edges.forEach {
        // We just put these elements in the `nodesRemapped` above. They MUST be in the map.
        val newNodeU = nodesRemapped[it.nodeU()]!!
        val newNodeV = nodesRemapped[it.nodeV()]!!
        mutableGraph.putEdge(newNodeU, newNodeV)
    }

    return mutableGraph
}
