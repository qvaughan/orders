package com.app.event

import com.app.order.Order

/**
 * Service that allows components to subscribe to various events emitted by the app.
 */
interface EventsService {

    /**
     * Subscribe to all events associated with any event subscription interfaces [subscriber] may implement.
     * @param subscriber any object that implements at least one of the associated subscriber interfaces from Subscribers.kt
     */
    fun subscribe(subscriber: Any)

    /**
     * Emits an OrderCompleteEvent to subscribers for the given [order].
     *
     * @param order the order that has just completed
     */
    fun orderCompleted(order: Order)

    fun orderFailed(msg: String)

}


class DefaultEventsService : EventsService {

    private val subscribers = mutableMapOf<Event, MutableList<Any>>()

    override fun subscribe(subscriber: Any) {
        if (subscriber is OrderCompleteEventSubscriber) {
            subscribers.computeIfAbsent(Event.OrderCompleteEvent) { mutableListOf() }.add(subscriber)
        }

        if (subscriber is OrderFailedEventSubscriber) {
            subscribers.computeIfAbsent(Event.OrderFailedEvent) { mutableListOf() }.add(subscriber)
        }

    }

    override fun orderCompleted(order: Order) {
        subscribers.get(Event.OrderCompleteEvent)?.forEach {
            if (it is OrderCompleteEventSubscriber)
                it.orderCompleted(order)
        }
    }

    override fun orderFailed(msg: String) {
        subscribers.get(Event.OrderFailedEvent)?.forEach {
           if (it is OrderFailedEventSubscriber)
               it.orderFailed(msg)
        }
    }
}