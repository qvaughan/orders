package com.app.event

import com.app.order.Order
import java.util.concurrent.ConcurrentHashMap

interface EventsService {

    fun subscribe(subscriber: Any)

    fun orderCompleted(order: Order)

}


class DefaultEventsService : EventsService {

    private val subscribers = mutableMapOf<Event, MutableList<Any>>()

    override fun subscribe(subscriber: Any) {
        if (subscriber is OrderCompleteEventSubscriber) {
            subscribers.computeIfAbsent(Event.OrderCompleteEvent) { mutableListOf() }.add(subscriber)
        }
    }

    override fun orderCompleted(order: Order) {
        subscribers.get(Event.OrderCompleteEvent)?.forEach {
            if (it is OrderCompleteEventSubscriber)
                it.orderCompleted(order)
        }
    }

}