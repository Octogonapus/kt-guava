/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.guavautil.collections

import com.google.common.collect.ImmutableListMultimap
import com.google.common.collect.Multimap

fun <K, V> Multimap<K, V>.toImmutableListMultimap(): ImmutableListMultimap<K, V> =
    ImmutableListMultimap.copyOf(this)

fun <K, V> immutableListMultimapOf(vararg pairs: Pair<K, V>): ImmutableListMultimap<K, V> {
    val builder = ImmutableListMultimap.builder<K, V>()

    pairs.forEach {
        builder.put(it.first, it.second)
    }

    return builder.build()
}

fun <K, V> emptyImmutableListMultimap(): ImmutableListMultimap<K, V> = ImmutableListMultimap.of()

operator fun <K, V> ImmutableListMultimap<K, V>.plus(other: Multimap<K, V>):
    ImmutableListMultimap<K, V> =
    ImmutableListMultimap.builder<K, V>()
        .putAll(this)
        .putAll(other)
        .build()

@Suppress("UnstableApiUsage")
operator fun <K, V> ImmutableListMultimap<K, V>.plus(other: Iterable<Map.Entry<K, V>>):
    ImmutableListMultimap<K, V> =
    ImmutableListMultimap.builder<K, V>()
        .putAll(this)
        .putAll(other)
        .build()
