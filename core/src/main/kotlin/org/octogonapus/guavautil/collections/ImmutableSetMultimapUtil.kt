/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.guavautil.collections

import com.google.common.collect.ImmutableSetMultimap
import com.google.common.collect.Multimap

fun <K, V> Multimap<K, V>.toImmutableSetMultimap(): ImmutableSetMultimap<K, V> =
    ImmutableSetMultimap.copyOf(this)

fun <K, V> Iterable<Pair<K, Iterable<V>>>.toImmutableSetMultimap(): ImmutableSetMultimap<K, V> =
    immutableSetMultimapOf(
        flatMap { key ->
            key.second.map { value ->
                key.first to value
            }
        }
    )

fun <K, V> immutableSetMultimapOf(pairs: Iterable<Pair<K, V>>): ImmutableSetMultimap<K, V> {
    val builder = ImmutableSetMultimap.builder<K, V>()

    pairs.forEach {
        builder.put(it.first, it.second)
    }

    return builder.build()
}

fun <K, V> immutableSetMultimapOf(vararg pairs: Pair<K, V>): ImmutableSetMultimap<K, V> {
    // Don't turn pairs into a list to save a copy
    val builder = ImmutableSetMultimap.builder<K, V>()

    pairs.forEach {
        builder.put(it.first, it.second)
    }

    return builder.build()
}

fun <K, V> emptyImmutableSetMultimap(): ImmutableSetMultimap<K, V> = ImmutableSetMultimap.of()

operator fun <K, V> ImmutableSetMultimap<K, V>.plus(other: Multimap<K, V>):
    ImmutableSetMultimap<K, V> =
    ImmutableSetMultimap.builder<K, V>()
        .putAll(this)
        .putAll(other)
        .build()

@Suppress("UnstableApiUsage")
operator fun <K, V> ImmutableSetMultimap<K, V>.plus(other: Iterable<Map.Entry<K, V>>):
    ImmutableSetMultimap<K, V> =
    ImmutableSetMultimap.builder<K, V>()
        .putAll(this)
        .putAll(other)
        .build()
