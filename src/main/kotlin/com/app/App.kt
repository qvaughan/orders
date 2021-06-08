package com.app

import com.app.order.DefaultOrderTerminalController
import com.app.order.DefaultOrderTerminalView


fun main() {
    val orderService = DefaultOrderService()
    val orderView = DefaultOrderTerminalView()
    val orderController = DefaultOrderTerminalController(orderView, orderService)
    orderController.start()
}
