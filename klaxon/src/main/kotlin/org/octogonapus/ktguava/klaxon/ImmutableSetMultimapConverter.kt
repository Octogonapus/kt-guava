/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.klaxon

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.google.common.collect.ImmutableSetMultimap
import com.google.common.collect.SetMultimap
import org.octogonapus.ktguava.collections.toImmutableSetMultimap

/**
 * A [Converter] which can convert an [ImmutableSetMultimap] by treating it like [Map] of
 * [Set].
 */
fun Klaxon.immutableSetMultimapConverter() = object : Converter {

    override fun canConvert(cls: Class<*>) = cls == ImmutableSetMultimap::class.java

    override fun fromJson(jv: JsonValue) =
        parseFromJsonObject<Map<*, Set<*>>>(jv.obj!!)!!.toImmutableSetMultimap()

    override fun toJson(value: Any) = toJsonString((value as SetMultimap<*, *>).asMap())
}
