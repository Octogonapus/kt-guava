/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.guavautil.collections

import com.google.common.collect.ImmutableMap

fun <K, V> Iterable<Pair<K, V>>.toImmutableMap(): ImmutableMap<K, V> =
    ImmutableMap.copyOf(toMap())

fun <K, V> immutableMapOf(vararg elements: Pair<K, V>): ImmutableMap<K, V> =
    ImmutableMap.copyOf(elements.toMap())

fun <K, V> emptyImmutableMap(): ImmutableMap<K, V> = ImmutableMap.of()
