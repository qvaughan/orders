package com.app.inventory

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DefaultInventoryServiceTest {
    lateinit var inventoryService: DefaultInventoryService

    @BeforeEach
    fun beforeEach(){
        inventoryService = DefaultInventoryService()
    }

    @Test
    fun `pick 3 apples from inventory if there are 5 and update the remaining count to 2`() {
        inventoryService.pick("Apple")
        inventoryService.pick("Apple")
        val price = inventoryService.pick("Apple")
        assertThat(price).isEqualTo(BigDecimal("0.60"))
        assertThat(inventoryService.remaining("Apple")).isEqualTo(2)
    }

    @Test
    fun `pick 6 apples from inventory if there are 5 and the 6th should return null`() {
        inventoryService.pick("Apple")
        inventoryService.pick("Apple")
        inventoryService.pick("Apple")
        inventoryService.pick("Apple")
        inventoryService.pick("Apple")
        val price = inventoryService.pick("Apple")
        assertThat(price).isNull()
    }

    @Test
    fun `increment inventory for returned item by one`() {
        assertThat(inventoryService.remaining("apple")).isEqualTo(5)
        inventoryService.returnItem("apple")
        assertThat(inventoryService.remaining("apple")).isEqualTo(6)
    }

}