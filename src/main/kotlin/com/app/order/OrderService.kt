package com.app

import com.app.event.EventsService
import com.app.inventory.InventoryItem
import com.app.inventory.InventoryService
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
    fun submitOrder(items: List<String>) : Order?

}

/**
 * Default implementation of OrderService
 */
class DefaultOrderService(val offerService: OfferService, val eventsService: EventsService, val inventoryService: InventoryService) : OrderService {


    override fun submitOrder(order: List<String>): Order? {
        val productItems = mutableListOf<LineItem>()
        try {
            order.mapTo(productItems) {
                val price = inventoryService.pick(it) ?: throw Exception("Not enough $it for order: $order.")
                LineItem(it, price)
            }
        } catch (e: Exception) {
            productItems.forEach {
                inventoryService.returnItem(it.item)
            }
            eventsService.orderFailed(e.message ?: "Order failed for unknown reason.")
            return null
        }
        val offerItems = offerService.findOffers(productItems)
        val items = productItems + offerItems
        val cost = items.fold(BigDecimal.ZERO) { acc, item -> acc + item.price }
        val result = Order(items, cost, LocalDate.now().plusDays(3))
        eventsService.orderCompleted(result)
        return result
    }

}