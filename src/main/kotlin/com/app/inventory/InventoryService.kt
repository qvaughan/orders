package com.app.inventory

import java.math.BigDecimal

interface InventoryService {

    fun pick(itemName: String) : BigDecimal?

    fun returnItem(itemName: String)

    fun remaining(itemName: String) : Int

}

class DefaultInventoryService : InventoryService {

    private val inventory = listOf(InventoryItem("apple", BigDecimal(".60"), 5),
        InventoryItem("orange", BigDecimal(".25"), 5))

    override fun pick(itemName: String): BigDecimal? {
        val item = inventory.find { it.name.equals(itemName, true) }
        if (item != null && item.remaining >= 1) {
            item.remaining--
            return item.price
        }
        return null
    }

    override fun returnItem(itemName: String) {
        val item = inventory.find { it.name.equals(itemName, true) }
        if (item != null) {
            item.remaining++
        }
    }

    override fun remaining(itemName: String) = inventory.find { it.name.equals(itemName, true)  }?.remaining ?: 0
}