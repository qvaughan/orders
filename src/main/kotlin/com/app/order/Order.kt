package com.app.order

import java.math.BigDecimal

data class LineItem(val item: String, val price: BigDecimal)
data class Order(val items: List<LineItem>, val totalCost: BigDecimal)
