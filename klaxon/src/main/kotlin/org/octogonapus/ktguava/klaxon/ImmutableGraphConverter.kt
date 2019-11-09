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
import com.google.common.graph.ImmutableGraph
import org.octogonapus.ktguava.collections.toImmutableGraph

/**
 * A [Converter] which can convert an [ImmutableGraph] by delegating to [graphConverter].
 */
inline fun <reified T : Any> Klaxon.immutableGraphConverter(): Converter {
    val impl = graphConverter<T>()
    return object : Converter by impl {
        override fun canConvert(cls: Class<*>) = ImmutableGraph::class.java.isAssignableFrom(cls)

        override fun fromJson(jv: JsonValue): Any? {
            @Suppress("UNCHECKED_CAST")
            return (impl.fromJson(jv) as Graph<T>).toImmutableGraph()
        }
    }
}
