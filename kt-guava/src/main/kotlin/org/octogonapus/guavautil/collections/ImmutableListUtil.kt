/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.guavautil.collections

import com.google.common.collect.ImmutableList

fun <T> Iterable<T>.toImmutableList(): ImmutableList<T> = ImmutableList.copyOf(this)

fun <T> immutableListOf(vararg elements: T): ImmutableList<T> =
    ImmutableList.copyOf(elements.toList())

fun <T> emptyImmutableList(): ImmutableList<T> = ImmutableList.of()

operator fun <T> ImmutableList<T>.plus(other: Iterable<T>): ImmutableList<T> =
    ImmutableList.builder<T>()
        .addAll(this)
        .addAll(other)
        .build()
