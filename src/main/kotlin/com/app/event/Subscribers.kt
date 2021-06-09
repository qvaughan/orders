package com.app.event

import com.app.order.Order

interface OrderCompleteEventSubscriber {

    fun orderCompleted(order: Order)

}

interface OrderFailedEventSubscriber {

    fun orderFailed(msg: String)

}
