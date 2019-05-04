/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.collections

import com.google.common.collect.ImmutableList

fun <T> Iterable<T>.toImmutableList(): ImmutableList<T> = ImmutableList.copyOf(this)

fun <T> Array<out T>.toImmutableList(): ImmutableList<T> = ImmutableList.copyOf(this)

fun <T> immutableListOf(vararg elements: T): ImmutableList<T> =
    ImmutableList.copyOf(elements.toList())

fun <T> emptyImmutableList(): ImmutableList<T> = ImmutableList.of()

operator fun <T> ImmutableList<out T>.plus(other: Iterable<T>): ImmutableList<T> =
    ImmutableList.builder<T>()
        .addAll(this)
        .addAll(other)
        .build()

operator fun <T> ImmutableList<out T>.minus(other: Iterable<T>): ImmutableList<T> =
    toMutableList().apply {
        removeAll(other)
    }.toImmutableList()

fun ByteArray.toImmutableList(): ImmutableList<Byte> = toList().toImmutableList()

fun CharArray.toImmutableList(): ImmutableList<Char> = toList().toImmutableList()

fun ShortArray.toImmutableList(): ImmutableList<Short> = toList().toImmutableList()

fun IntArray.toImmutableList(): ImmutableList<Int> = toList().toImmutableList()

fun LongArray.toImmutableList(): ImmutableList<Long> = toList().toImmutableList()

fun FloatArray.toImmutableList(): ImmutableList<Float> = toList().toImmutableList()

fun DoubleArray.toImmutableList(): ImmutableList<Double> = toList().toImmutableList()

fun BooleanArray.toImmutableList(): ImmutableList<Boolean> = toList().toImmutableList()
