/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
@file:Suppress("UnstableApiUsage")

package org.octogonapus.ktguava.klaxon

import com.beust.klaxon.Klaxon
import com.google.common.graph.GraphBuilder
import com.google.common.graph.ImmutableGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.octogonapus.ktguava.collections.toImmutableGraph

internal class ImmutableGraphConverterTest {

    internal data class TestData(
        @ConvertImmutableGraph
        val list: ImmutableGraph<String>
    )

    private val klaxon = Klaxon().also {
        it.fieldConverter(ConvertImmutableGraph::class, it.immutableGraphConverter<String>())
    }

    private val testGraph = GraphBuilder.directed()
        .allowsSelfLoops(false)
        .build<String>()
        .apply {
            putEdge("A", "B")
            putEdge("B", "C")
        }.toImmutableGraph()

    internal data class ComplexTestData(
        @ConvertImmutableGraph
        val list: ImmutableGraph<Foo>
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is ComplexTestData) return false

            if (list.nodes() != other.list.nodes()) return false
            if (list.edges() != other.list.edges()) return false

            return true
        }

        override fun hashCode(): Int {
            return list.hashCode()
        }
    }

    internal data class Foo(val arg1: String)

    private val complexKlaxon = Klaxon().also {
        it.fieldConverter(ConvertImmutableGraph::class, it.immutableGraphConverter<Foo>())
    }

    private val testComplexGraph = GraphBuilder.directed()
        .allowsSelfLoops(false)
        .build<Foo>()
        .apply {
            putEdge(Foo("A"), Foo("B"))
            putEdge(Foo("B"), Foo("C"))
        }.toImmutableGraph()

    @Test
    fun `test canConvert`() {
        assertTrue(klaxon.immutableGraphConverter<String>().canConvert(ImmutableGraph::class.java))
    }

    @Test
    @Disabled("https://github.com/cbeust/klaxon/issues/293")
    fun `test converting simple data type`() {
        assertEquals(
            TestData(testGraph),
            klaxon.parse<TestData>(klaxon.toJsonString(TestData(testGraph)))
        )
    }

    @Test
    fun `test conversion with complex data type`() {
        assertEquals(
            ComplexTestData(testComplexGraph),
            complexKlaxon.parse<ComplexTestData>(
                complexKlaxon.toJsonString(
                    ComplexTestData(testComplexGraph)
                )
            )
        )
    }
}
