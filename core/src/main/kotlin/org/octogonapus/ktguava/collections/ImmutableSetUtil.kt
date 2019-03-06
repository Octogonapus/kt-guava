/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.google.common.collect.ImmutableSet

fun <T> Iterable<T>.toImmutableSet(): ImmutableSet<T> = ImmutableSet.copyOf(this)

fun <T> Array<out T>.toImmutableSet(): ImmutableSet<T> = ImmutableSet.copyOf(this)

fun <T> immutableSetOf(vararg elements: T): ImmutableSet<T> = ImmutableSet.copyOf(elements.toSet())

fun <T> emptyImmutableSet(): ImmutableSet<T> = ImmutableSet.of()

operator fun <T> ImmutableSet<out T>.plus(other: Iterable<T>): ImmutableSet<T> =
    ImmutableSet.builder<T>()
        .addAll(this)
        .addAll(other)
        .build()

operator fun <T> ImmutableSet<out T>.minus(other: Iterable<T>): ImmutableSet<T> =
    toMutableSet().apply {
        removeAll(other)
    }.toImmutableSet()
