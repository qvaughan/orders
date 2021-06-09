package com.app.order

import java.math.BigDecimal
import java.time.LocalDate

data class LineItem(val item: String, val price: BigDecimal)
data class Order(val items: List<LineItem>, val totalCost: BigDecimal, val estimatedDeliveryDate: LocalDate)
