package com.app.order

import com.app.OrderService
import com.app.event.DefaultEventsService
import com.app.event.EventsService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.InputStream
import java.math.BigDecimal
import java.time.LocalDate

class DefaultOrderTerminalControllerTest {

    lateinit var orderService: OrderService
    lateinit var orderView: OrderTerminalView
    lateinit var orderController: DefaultOrderTerminalController
    lateinit var mockInputStream: InputStream
    lateinit var eventsService: EventsService

    @BeforeEach
    fun beforeEach(){
        mockInputStream = mock()
        System.setIn(mockInputStream)
        orderView = mock()
        orderService = mock()
        eventsService = DefaultEventsService()
        orderController = DefaultOrderTerminalController(orderView, orderService, eventsService)
    }

    @Test
    fun `prompt user for order on start`() {
        // Immediately return newline character when prompted for input
        "q\n".fold(whenever(mockInputStream.read())) { mock, char -> mock.thenReturn(char.toInt()) }
        orderController.start()
        verify(orderView).promptForOrder()
    }

    @Test
    fun `read user order from command line and submit it to the order service`() {
        // Enter an order of Apple, Apple, Orange, Apple then press enter
        "Apple, Apple, Orange, Apple\nq\n".fold(whenever(mockInputStream.read())) { mock, char -> mock.thenReturn(char.toInt()) }
        orderController.start()
        verify(orderService).submitOrder(listOf("Apple", "Apple", "Orange", "Apple"))
    }

    @Test
    fun `call order view to print order complete message when called by event service`(){
        val order = Order(listOf(LineItem("Apple", BigDecimal(".60"))), BigDecimal(".60"), LocalDate.now().plusDays(3))
        eventsService.orderCompleted(order)
        verify(orderView).showOrderCompleted(order)
    }

    @Test
    fun `call order view to print order failed message when called by event service`(){
        eventsService.orderFailed("DUMMY MESSAGE")
        verify(orderView).showOrderFailed("DUMMY MESSAGE")
    }
}