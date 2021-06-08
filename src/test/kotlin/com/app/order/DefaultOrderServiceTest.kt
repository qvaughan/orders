package com.app.order

import com.app.DefaultOrderService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DefaultOrderServiceTest {

    lateinit var orderService: DefaultOrderService

    @BeforeEach
    fun beforeEach(){
        orderService = DefaultOrderService()
    }

    @Test
    fun `receive order of three apples and one orange and calculates the order cost is 2 dollars and 5 cents` () {
        val order = listOf("Apple", "Apple", "Orange", "Apple")
        val orderResponse = orderService.submitOrder(order)
        assertThat(orderResponse.totalCost).isEqualTo(BigDecimal("2.05"))
    }

}