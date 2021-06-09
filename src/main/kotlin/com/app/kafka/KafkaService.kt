package com.app.kafka

import com.app.event.DefaultEventsService
import com.app.event.OrderCompleteEventSubscriber
import com.app.event.OrderFailedEventSubscriber
import com.app.order.Order
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.net.InetAddress

class KafkaService(eventsService: DefaultEventsService) : OrderCompleteEventSubscriber,
    OrderFailedEventSubscriber {

    val configs = mutableMapOf<String, Any?>(
        "client.id" to InetAddress.getLocalHost().getHostName(),
        "bootstrap.servers" to "localhost:9092",
        "acks" to "all",
        "key.serializer" to StringSerializer::class.qualifiedName,
        "value.serializer" to StringSerializer::class.qualifiedName
    )

    init {
        eventsService.subscribe(this)
    }

    override fun orderCompleted(order: Order) {
        KafkaProducer<String, String>(configs).use {
            it.send(ProducerRecord("order-submitted", "details", order.toString()))
        }
    }

    override fun orderFailed(msg: String) {
        KafkaProducer<String, String>(configs).use {
            it.send(ProducerRecord("order-failed", "details", msg))
        }
    }
}