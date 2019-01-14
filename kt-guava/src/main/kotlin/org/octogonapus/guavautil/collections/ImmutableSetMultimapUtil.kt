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

fun <K, V> immutableSetMultimapOf(vararg pairs: Pair<K, V>): ImmutableSetMultimap<K, V> {
    val builder = ImmutableSetMultimap.builder<K, V>()

    pairs.forEach {
        builder.put(it.first, it.second)
    }

    return builder.build()
}

fun <K, V> emptyImmutableSetMultimap(): ImmutableSetMultimap<K, V> = ImmutableSetMultimap.of()

operator fun <K, V> ImmutableSetMultimap<K, V>.plus(other: ImmutableSetMultimap<K, V>):
        ImmutableSetMultimap<K, V> =
        ImmutableSetMultimap.builder<K, V>()
                .putAll(this)
                .putAll(other)
                .build()
