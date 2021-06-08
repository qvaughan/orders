package com.app.order

import com.app.OrderService

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
class DefaultOrderTerminalController(val orderView: OrderTerminalView, val orderService: OrderService, ) : OrderTerminalController {

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

}