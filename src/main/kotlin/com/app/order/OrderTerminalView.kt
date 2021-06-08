package com.app.order


/**
 * View for submitting orders from the terminal.
 */
interface OrderTerminalView {

    /**
     * Prompts the user via System.out for submitting an order.
     */
    fun promptForOrder()

}


/**
 * Default implementation for the OrderTerminalView
 */
class DefaultOrderTerminalView : OrderTerminalView {

    override fun promptForOrder() {
        println("Enter order or q to quit:")
    }

}