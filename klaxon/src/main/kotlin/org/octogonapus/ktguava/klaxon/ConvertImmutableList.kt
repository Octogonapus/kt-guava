/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.octogonapus.ktguava.klaxon

/**
 * Annotate fields of type ImmutableList which must be converted by Klaxon with this and use a
 * Klaxon.fieldConverter.
 */
@Target(AnnotationTarget.FIELD)
annotation class ConvertImmutableList
