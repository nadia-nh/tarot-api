package com.flowworks.tarotapi.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CardRepositoryTest {

    @Test
    fun `loads all 78 cards`() {
        assertEquals(78, CardRepository.getAll().size)
    }

    @Test
    fun `major arcana has 22 cards with null rank`() {
        val majors = CardRepository.getAll(suit = "major_arcana")

        assertEquals(22, majors.size)
        assertTrue(majors.all { it.rank == null })
    }

    @Test
    fun `each minor suit has 14 cards`() {
        listOf("wands", "cups", "swords", "pentacles").forEach { suit ->
            assertEquals(14, CardRepository.getAll(suit = suit).size, "suit=$suit")
        }
    }

    @Test
    fun `unknown suit filter returns no cards`() {
        assertEquals(0, CardRepository.getAll(suit = "not-a-suit").size)
    }

    @Test
    fun `getById returns the fool with expected content`() {
        val fool = CardRepository.getById("the_fool")

        assertEquals("The Fool", fool?.name)
        assertEquals("major_arcana", fool?.suit)
        assertEquals(0, fool?.value)
        assertTrue(fool!!.upright.keywords.contains("new beginnings"))
        assertTrue(fool.upright.meaning.isNotBlank())
        assertTrue(fool.upright.reflection.isNotBlank())
        assertTrue(fool.reversed.keywords.contains("recklessness"))
    }

    @Test
    fun `getById returns null for a missing card`() {
        assertNull(CardRepository.getById("not-a-card"))
    }
}
