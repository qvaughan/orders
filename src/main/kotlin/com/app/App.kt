package com.app

import com.app.offer.DefaultOfferService
import com.app.order.DefaultOrderTerminalController
import com.app.order.DefaultOrderTerminalView


fun main() {
    val offerService = DefaultOfferService()
    val orderService = DefaultOrderService(offerService)
    val orderView = DefaultOrderTerminalView()
    val orderController = DefaultOrderTerminalController(orderView, orderService)
    orderController.start()
}
