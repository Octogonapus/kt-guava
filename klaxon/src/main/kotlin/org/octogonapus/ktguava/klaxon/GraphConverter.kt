/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
@file:Suppress("UnstableApiUsage")

package org.octogonapus.ktguava.klaxon

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder

/**
 * A [Converter] which can convert an [Graph] by treating it like an adjacency list.
 */
inline fun <reified T : Any> Klaxon.graphConverter() = object : Converter {

    override fun canConvert(cls: Class<*>) = Graph::class.java.isAssignableFrom(cls)

    override fun fromJson(jv: JsonValue): Any? {
        val data = parseFromJsonObject<GraphKlaxonData>(jv.obj!!)!!
        val graph = if (data.directed) {
            GraphBuilder.directed()
        } else {
            GraphBuilder.undirected()
        }.allowsSelfLoops(data.allowSelfLoops).build<T>()

        data.adjacencyListKeys
            .zip(data.adjacencyListValues)
            .forEach { (nodeJson, adjacentNodesJson) ->
                val node = parse<T>(nodeJson)!!
                val adjacentNodes = adjacentNodesJson.map { parse<T>(it)!! }
                adjacentNodes.forEach { adjacentNode ->
                    graph.putEdge(node, adjacentNode)
                }
            }

        return graph
    }

    @Suppress("UNCHECKED_CAST")
    override fun toJson(value: Any): String {
        value as Graph<T>
        val adjacencyList = value.adjacencyList()
        return toJsonString(
            GraphKlaxonData(
                value.isDirected,
                value.allowsSelfLoops(),
                adjacencyList.map { toJsonString(it.first) },
                adjacencyList.map { it.second.map { toJsonString(it) } }
            )
        )
    }
}

data class GraphKlaxonData(
    val directed: Boolean,
    val allowSelfLoops: Boolean,
    val adjacencyListKeys: List<String>,
    val adjacencyListValues: List<List<String>>
)

fun <T : Any> Graph<T>.adjacencyList(): List<Pair<T, Set<T>>> = nodes().map {
    it to successors(it)
}
