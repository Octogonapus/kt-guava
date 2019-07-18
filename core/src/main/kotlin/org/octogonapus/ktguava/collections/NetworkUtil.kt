/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
@file:Suppress("UnstableApiUsage")

package org.octogonapus.ktguava.collections

import com.google.common.graph.EndpointPair
import com.google.common.graph.Network

/**
 * Finds all in edges and the nodes on the ends of those edges given a starting node [node].
 * This network must be directed.
 *
 * @param node The starting node to search from.
 * @return All in edges with their nodes.
 */
fun <N : Any, E : Any> Network<N, out E>.inNodesAndEdges(
    node: N
): Set<Pair<EndpointPair<N>, E>> {
    require(isDirected)

    val inEdges = inEdges(node)
    return adjacentNodes(node).map {
        EndpointPair.ordered(it, node) to edgeConnecting(it, node)
    }.filter { (_, edge) ->
        edge.isPresent && edge.get() in inEdges
    }.mapTo(hashSetOf()) { (node, edge) ->
        node to edge.get()
    }
}

/**
 * Finds all nodes on the ends of the in edges for a starting node [node]. This network must be
 * directed.
 *
 * @param node The starting node to search from.
 * @return All nodes on the ends of the in edges.
 */
fun <N : Any, E : Any> Network<N, out E>.inNodes(node: N): Set<N> =
    inNodesAndEdges(node).mapTo(hashSetOf()) {
        it.first.nodeU()
    }

/**
 * Finds all out edges and the nodes on the ends of those edges given a starting node [node].
 * This network must be directed.
 *
 * @param node The starting node to search from.
 * @return All out edges with their nodes.
 */
fun <N : Any, E : Any> Network<N, out E>.outNodesAndEdges(
    node: N
): Set<Pair<EndpointPair<N>, E>> {
    require(isDirected)

    val outEdges = outEdges(node)
    return adjacentNodes(node).map {
        EndpointPair.ordered(node, it) to edgeConnecting(node, it)
    }.filter { (_, edge) ->
        edge.isPresent && edge.get() in outEdges
    }.mapTo(hashSetOf()) { (node, edge) ->
        node to edge.get()
    }
}

/**
 * Finds all nodes on the ends of the out edges for a starting node [node]. This network must be
 * directed.
 *
 * @param node The starting node to search from.
 * @return All nodes on the ends of the out edges.
 */
fun <N : Any, E : Any> Network<N, out E>.outNodes(node: N): Set<N> =
    outNodesAndEdges(node).mapTo(hashSetOf()) {
        it.first.nodeV()
    }
