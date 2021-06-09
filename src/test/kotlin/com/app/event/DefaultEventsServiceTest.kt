package com.app.event

import com.app.order.LineItem
import com.app.order.Order
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.timeout
import org.mockito.kotlin.verify
import java.math.BigDecimal
import java.time.LocalDate

class DefaultEventsServiceTest {

    lateinit var eventsService: DefaultEventsService
    lateinit var mockOrderCompleteSubscriber: OrderCompleteEventSubscriber
    lateinit var mockOrderFailedSubscriber: OrderFailedEventSubscriber
    
    @BeforeEach
    fun beforeEach() {
        eventsService = DefaultEventsService()
        mockOrderCompleteSubscriber = mock()
        mockOrderFailedSubscriber = mock()
    }

    @Test
    fun `notify subscribers when an order is processed successfully`() {
        eventsService.subscribe(mockOrderCompleteSubscriber)
        val order = Order(listOf(LineItem("Apple", BigDecimal(".60"))), BigDecimal(".60"), LocalDate.now().plusDays(3))
        eventsService.orderCompleted(order)
        verify(mockOrderCompleteSubscriber, timeout(5000)).orderCompleted(order)
    }
    
    @Test
    fun `notify subscribers when an order fails`() {
        eventsService.subscribe(mockOrderFailedSubscriber)
        eventsService.orderFailed("DUMMY MESSAGE")
        verify(mockOrderFailedSubscriber, timeout(5000)).orderFailed("DUMMY MESSAGE")
    }

}