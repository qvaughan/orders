package com.app

import com.app.event.DefaultEventsService
import com.app.inventory.DefaultInventoryService
import com.app.kafka.KafkaService
import com.app.offer.DefaultOfferService
import com.app.order.DefaultOrderTerminalController
import com.app.order.DefaultOrderTerminalView


fun main() {
    val offerService = DefaultOfferService()
    val eventsService = DefaultEventsService()
    val kafkaService = KafkaService(eventsService)
    val inventoryService = DefaultInventoryService()
    val orderService = DefaultOrderService(offerService, eventsService, inventoryService)
    val orderView = DefaultOrderTerminalView()
    val orderController = DefaultOrderTerminalController(orderView, orderService, eventsService)
    orderController.start()
}
