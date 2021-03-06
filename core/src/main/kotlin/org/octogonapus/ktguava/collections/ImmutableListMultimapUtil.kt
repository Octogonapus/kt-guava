/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.google.common.collect.ImmutableListMultimap
import com.google.common.collect.Multimap

fun <K, V> Multimap<out K, out V>.toImmutableListMultimap(): ImmutableListMultimap<K, V> =
    ImmutableListMultimap.copyOf(this)

fun <K, V> Iterable<Pair<K, Iterable<V>>>.toImmutableListMultimap(): ImmutableListMultimap<K, V> =
    immutableListMultimapOf(
        flatMap { key ->
            key.second.map { value ->
                key.first to value
            }
        }
    )

fun <K, V> Map<K, Iterable<V>>.toImmutableListMultimap(): ImmutableListMultimap<K, V> =
    entries.map { it.toPair() }.toImmutableListMultimap()

fun <K, V> immutableListMultimapOf(pairs: Iterable<Pair<K, V>>): ImmutableListMultimap<K, V> {
    val builder = ImmutableListMultimap.builder<K, V>()

    pairs.forEach {
        builder.put(it.first, it.second)
    }

    return builder.build()
}

fun <K, V> immutableListMultimapOf(vararg pairs: Pair<K, V>): ImmutableListMultimap<K, V> {
    // Don't turn pairs into a list to save a copy
    val builder = ImmutableListMultimap.builder<K, V>()

    pairs.forEach {
        builder.put(it.first, it.second)
    }

    return builder.build()
}

fun <K, V> emptyImmutableListMultimap(): ImmutableListMultimap<K, V> = ImmutableListMultimap.of()

operator fun <K, V> ImmutableListMultimap<out K, out V>.plus(other: Multimap<K, V>):
    ImmutableListMultimap<K, V> =
    ImmutableListMultimap.builder<K, V>()
        .putAll(this)
        .putAll(other)
        .build()

@Suppress("UnstableApiUsage")
operator fun <K, V> ImmutableListMultimap<out K, out V>.plus(other: Iterable<Map.Entry<K, V>>):
    ImmutableListMultimap<K, V> =
    ImmutableListMultimap.builder<K, V>()
        .putAll(this)
        .putAll(other)
        .build()
