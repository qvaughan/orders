package com.app

import com.app.order.Order
import java.math.BigDecimal

/**
 * Service for processing Orders
 */
interface OrderService {

    /**
     * Submits an order for processing.
     * @param items List of items in order
     * @return The submitted order
     */
    fun submitOrder(items: List<String>) : Order

}

/**
 * Default implementation of OrderService
 */
class DefaultOrderService : OrderService {

    private val prices = mapOf("apple" to BigDecimal(".60"), "orange" to BigDecimal(".25"))

    override fun submitOrder(order: List<String>): Order {
        val cost = order.fold(BigDecimal.ZERO) { acc, item ->
            acc.add(prices[item.toLowerCase()] ?: BigDecimal.ZERO)
        }
        return Order(order, cost)
    }

}