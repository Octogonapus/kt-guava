/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.klaxon

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import com.google.common.collect.ImmutableList
import org.octogonapus.ktguava.collections.toImmutableList

/**
 * A [Converter] which can convert an [ImmutableList] by treating it like a [List].
 */
fun Klaxon.immutableListConverter() = object : Converter {

    override fun canConvert(cls: Class<*>) = cls == ImmutableList::class.java

    override fun fromJson(jv: JsonValue) =
        parseFromJsonArray<List<*>>(jv.array!!)!!.toImmutableList()

    override fun toJson(value: Any) = toJsonString(value as List<*>)
}
