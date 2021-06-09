package com.app.order

import com.app.OrderService
import com.app.event.EventsService
import com.app.event.OrderCompleteEventSubscriber
import com.app.event.OrderFailedEventSubscriber

/**
 * Controller for submitting orders from the terminal.
 */
interface OrderTerminalController {

    /**
     * Starts the order terminal controller and prompts the user for an order.
     */
    fun start()

}

/**
 * Default implementation for the OrderTerminalController
 */
class DefaultOrderTerminalController(val orderView: OrderTerminalView, val orderService: OrderService, val eventsService: EventsService) : OrderTerminalController, OrderCompleteEventSubscriber, OrderFailedEventSubscriber {

    init {
        eventsService.subscribe(this)
    }

    override fun start() {
        while(true) {
            orderView.promptForOrder()
            val input = readLine()
            if (input == "q") {
                break
            }
            val order = input?.split(Regex("\\s*,\\s*")) ?: emptyList()
            orderService.submitOrder(order)
        }
    }

    override fun orderCompleted(order: Order) {
        orderView.showOrderCompleted(order)
    }

    override fun orderFailed(msg: String) {
        orderView.showOrderFailed(msg)
    }
}