package com.app.order


/**
 * View for submitting orders from the terminal.
 */
interface OrderTerminalView {

    /**
     * Prompts the user via System.out for submitting an order.
     */
    fun promptForOrder()

    fun showOrderCompleted(order: Order)

    fun showOrderFailed(msg: String)
}


/**
 * Default implementation for the OrderTerminalView
 */
class DefaultOrderTerminalView : OrderTerminalView {

    override fun promptForOrder() {
        println("Enter order or q to quit:")
    }

    override fun showOrderCompleted(order: Order) {
        println("Order completed: $order")
    }

    override fun showOrderFailed(msg: String) {
        println("Order failed: $msg")
    }
}