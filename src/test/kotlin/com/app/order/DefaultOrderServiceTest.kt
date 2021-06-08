package com.app.order

import com.app.DefaultOrderService
import com.app.offer.OfferService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.math.BigDecimal

class DefaultOrderServiceTest {

    lateinit var orderService: DefaultOrderService
    lateinit var mockOfferService: OfferService

    @BeforeEach
    fun beforeEach() {
        mockOfferService = mock()
        orderService = DefaultOrderService(mockOfferService)
    }

    @Test
    fun `receive order of three apples and one orange and calculates the order cost is 2 dollars and 5 cents` () {
        val order = listOf("Apple", "Apple", "Orange", "Apple")
        val orderResponse = orderService.submitOrder(order)
        assertThat(orderResponse.totalCost).isEqualTo(BigDecimal("2.05"))
    }

    @Test
    fun `discount orders with special offers found by offer service`() {
        whenever(mockOfferService.findOffers(any())).thenReturn(listOf(LineItem("Buy one apple get one free", BigDecimal("-.60"))))
        val order = listOf("Apple", "Apple", "Orange", "Orange")
        val orderResponse = orderService.submitOrder(order)
        assertThat(orderResponse.totalCost).isEqualTo(BigDecimal("1.10"))
    }

    @Test
    fun `list special offers found by offer service as line items`() {
        val offerItem = LineItem("Buy one apple get one free", BigDecimal("-.60"))
        whenever(mockOfferService.findOffers(any())).thenReturn(listOf(offerItem))
        val order = listOf("Apple", "Apple", "Orange", "Orange")
        val orderResponse = orderService.submitOrder(order)
        assertThat(orderResponse.items).contains(offerItem)
    }

}