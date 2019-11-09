/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
@file:Suppress("UnstableApiUsage")

package org.octogonapus.ktguava.klaxon

import com.beust.klaxon.Klaxon
import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class GraphConverterTest {

    internal data class TestData(
        @ConvertGraph
        val list: Graph<String>
    )

    private val klaxon = Klaxon().also {
        it.fieldConverter(ConvertGraph::class, it.graphConverter<String>())
    }

    private val testGraph = GraphBuilder.directed()
        .allowsSelfLoops(false)
        .build<String>()
        .apply {
            putEdge("A", "B")
            putEdge("B", "C")
        }

    internal data class ComplexTestData(
        @ConvertGraph
        val list: Graph<Foo>
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
        it.fieldConverter(ConvertGraph::class, it.graphConverter<Foo>())
    }

    private val testComplexGraph = GraphBuilder.directed()
        .allowsSelfLoops(false)
        .build<Foo>()
        .apply {
            putEdge(Foo("A"), Foo("B"))
            putEdge(Foo("B"), Foo("C"))
        }

    @Test
    fun `test canConvert`() {
        assertTrue(klaxon.graphConverter<String>().canConvert(Graph::class.java))
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
