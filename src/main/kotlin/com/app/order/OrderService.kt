package com.app

import com.app.event.EventsService
import com.app.offer.OfferService
import com.app.order.Order
import com.app.order.LineItem
import java.math.BigDecimal
import java.time.LocalDate

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
class DefaultOrderService(val offerService: OfferService, val eventsService: EventsService) : OrderService {

    private val prices = mapOf("apple" to BigDecimal(".60"), "orange" to BigDecimal(".25"))

    override fun submitOrder(order: List<String>): Order {
        val productItems = order.map { LineItem(it, prices[it.toLowerCase()] ?: BigDecimal.ZERO) }
        val offerItems = offerService.findOffers(productItems)
        val items = productItems + offerItems
        val cost = items.fold(BigDecimal.ZERO) { acc, item -> acc + item.price }
        val result = Order(items, cost, LocalDate.now().plusDays(3))
        eventsService.orderCompleted(result)
        return result
    }

}