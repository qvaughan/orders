package com.app.order

import com.app.OrderService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.InputStream

class DefaultOrderTerminalControllerTest {

    lateinit var orderService: OrderService
    lateinit var orderView: OrderTerminalView
    lateinit var orderController: DefaultOrderTerminalController
    lateinit var mockInputStream: InputStream

    @BeforeEach
    fun beforeEach(){
        mockInputStream = mock()
        System.setIn(mockInputStream)
        orderView = mock()
        orderService = mock()
        orderController = DefaultOrderTerminalController(orderView, orderService)
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

}