package com.app

import com.app.event.DefaultEventsService
import com.app.offer.DefaultOfferService
import com.app.order.DefaultOrderTerminalController
import com.app.order.DefaultOrderTerminalView


fun main() {
    val offerService = DefaultOfferService()
    val eventsService = DefaultEventsService()
    val orderService = DefaultOrderService(offerService, eventsService)
    val orderView = DefaultOrderTerminalView()
    val orderController = DefaultOrderTerminalController(orderView, orderService, eventsService)
    orderController.start()
}
