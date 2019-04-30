/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.klaxon

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.google.common.collect.ImmutableListMultimap
import com.google.common.collect.ListMultimap
import org.octogonapus.ktguava.collections.toImmutableListMultimap

/**
 * A [Converter] which can convert an [ImmutableListMultimap] by treating it like [Map] of
 * [List].
 */
fun Klaxon.immutableListMultimapConverter() = object : Converter {

    override fun canConvert(cls: Class<*>) = cls == ImmutableListMultimap::class.java

    override fun fromJson(jv: JsonValue) =
        parseFromJsonObject<Map<*, List<*>>>(jv.obj!!)!!.toImmutableListMultimap()

    override fun toJson(value: Any) = toJsonString((value as ListMultimap<*, *>).asMap())
}
