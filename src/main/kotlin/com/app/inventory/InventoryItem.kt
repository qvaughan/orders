package com.app.inventory

import java.math.BigDecimal

data class InventoryItem(val name: String, val price: BigDecimal, var remaining: Int)
