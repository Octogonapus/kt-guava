/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.google.common.graph.ImmutableValueGraph
import com.google.common.graph.MutableValueGraph
import com.google.common.graph.ValueGraph
import com.google.common.graph.ValueGraphBuilder

@Suppress("UnstableApiUsage")
fun <N : Any, V : Any> ValueGraph<N, V>.toImmutableValueGraph(): ImmutableValueGraph<N, V> =
    ImmutableValueGraph.copyOf(this)

/**
 * Maps the nodes of the [ValueGraph].
 *
 * @param transform A mapping function applied to the nodes.
 * @return A graph with mapped nodes.
 */
@Suppress("UnstableApiUsage")
fun <N : Any, V : Any, R : Any> ValueGraph<out N, out V>.mapNodes(
    transform: (N) -> R
): ValueGraph<R, V> = mapValueGraphNodes(transform)

/**
 * Maps the nodes of the [ValueGraph].
 *
 * @param transform A mapping function applied to the nodes.
 * @return A graph with mapped nodes.
 */
@Suppress("UnstableApiUsage")
fun <N : Any, V : Any, R : Any> ImmutableValueGraph<N, out V>.mapNodes(
    transform: (N) -> R
): ImmutableValueGraph<R, V> = mapValueGraphNodes(transform).toImmutableValueGraph()

@Suppress("UnstableApiUsage", "MapGetWithNotNullAssertionOperator")
private fun <N : Any, R : Any, V : Any> ValueGraph<N, out V>.mapValueGraphNodes(
    transform: (N) -> R
): MutableValueGraph<R, V> {
    val builder = if (isDirected) ValueGraphBuilder.directed() else ValueGraphBuilder.undirected()
    val mutableGraph = builder.expectedNodeCount(nodes().size).build<R, V>()
    val nodesRemapped = nodes().map { it to transform(it) }.toMap()

    nodesRemapped.values.forEach { mutableGraph.addNode(it) }

    edges().forEach {
        // We just put these elements in the `nodesRemapped` above. They MUST be in the map.
        val newNodeU = nodesRemapped[it.nodeU()]!!
        val newNodeV = nodesRemapped[it.nodeV()]!!
        mutableGraph.putEdgeValue(
            newNodeU,
            newNodeV,
            edgeValue(it.nodeU(), it.nodeV()).orElseThrow {
                IllegalStateException("Value was not present.")
            }
        )
    }

    return mutableGraph
}
