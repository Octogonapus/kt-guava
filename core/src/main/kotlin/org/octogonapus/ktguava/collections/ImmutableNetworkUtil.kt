/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.google.common.graph.ImmutableNetwork
import com.google.common.graph.MutableNetwork
import com.google.common.graph.Network
import com.google.common.graph.NetworkBuilder

@Suppress("UnstableApiUsage")
fun <N : Any, E : Any> Network<N, E>.toImmutableNetwork(): ImmutableNetwork<N, E> =
    ImmutableNetwork.copyOf(this)

@Suppress("UnstableApiUsage")
fun <N : Any, E : Any, R : Any> Network<out N, E>.mapNodes(
    transform: (N) -> R
): Network<R, E> = mapNetworkNodes(transform)

@Suppress("UnstableApiUsage")
fun <N : Any, E : Any, R : Any> ImmutableNetwork<out N, E>.mapNodes(
    transform: (N) -> R
): ImmutableNetwork<R, E> = mapNetworkNodes(transform).toImmutableNetwork()

@Suppress("UnstableApiUsage")
private fun <N : Any, E : Any, R : Any> Network<N, E>.mapNetworkNodes(
    transform: (N) -> R
): MutableNetwork<R, E> {
    val nodes = nodes()
    val edges = edges()
    val builder = if (isDirected) NetworkBuilder.directed() else NetworkBuilder.undirected()
    val mutableNetwork = builder.expectedNodeCount(nodes.size)
        .expectedEdgeCount(edges.size)
        .build<R, E>()

    edges.forEach { edge ->
        val incidentNodes = incidentNodes(edge)
        mutableNetwork.addEdge(
            transform(incidentNodes.nodeU()),
            transform(incidentNodes.nodeV()),
            edge
        )
    }

    return mutableNetwork
}
