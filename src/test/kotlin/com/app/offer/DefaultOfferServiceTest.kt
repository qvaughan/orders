package com.app.offer

import com.app.order.LineItem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DefaultOfferServiceTest {

    lateinit var offerService: DefaultOfferService

    @BeforeEach
    fun beforeEach(){
        offerService = DefaultOfferService()
    }

    @Test
    fun `not detect buy one apple get one free on orders with one apple`(){
        val order = listOf(LineItem("Apple", BigDecimal(".60")))
        val offerItems = offerService.findOffers(order)
        assertThat(offerItems).isEmpty()
    }

    @Test
    fun `detect buy one apple get one free on orders with two apples`(){
        val order = (0..1).map { LineItem("Apple", BigDecimal(".60")) }
        val offerItems = offerService.findOffers(order)
        assertThat(offerItems).containsExactly(LineItem("Buy one apple, get one free", BigDecimal("-0.60")))
    }

    @Test
    fun `detect buy one apple get one free only once on orders with three apples`(){
        val order = (0..2).map { LineItem("Apple", BigDecimal(".60")) }
        val offerItems = offerService.findOffers(order)
        assertThat(offerItems).containsExactly(LineItem("Buy one apple, get one free", BigDecimal("-0.60")))
    }
    @Test
    fun `detect buy one apple get one free twice on orders with four apples`(){
        val order = (0..3).map { LineItem("Apple", BigDecimal(".60")) }
        val offerItems = offerService.findOffers(order)
        assertThat(offerItems).containsExactly(LineItem("Buy one apple, get one free", BigDecimal("-0.60")), LineItem("Buy one apple, get one free", BigDecimal("-0.60")))
    }

    @Test
    fun `not detect buy two oranges get one free on orders with two oranges`(){
        val order = (0..1).map { LineItem("Orange", BigDecimal(".25")) }
        val offerItems = offerService.findOffers(order)
        assertThat(offerItems).isEmpty()
    }

    @Test
    fun `detect buy two oranges get one free on orders with three oranges`(){
        val order = (0..2).map { LineItem("Orange", BigDecimal(".25")) }
        val offerItems = offerService.findOffers(order)
        assertThat(offerItems).containsExactly(LineItem("Buy two oranges, get one free", BigDecimal("-0.25")))
    }

    @Test
    fun `detect buy two oranges get one free on orders with four oranges`(){
        val order = (0..3).map { LineItem("Orange", BigDecimal(".25")) }
        val offerItems = offerService.findOffers(order)
        assertThat(offerItems).containsExactly(LineItem("Buy two oranges, get one free", BigDecimal("-0.25")))
    }

    @Test
    fun `detect buy two oranges get one free on orders with five oranges`(){
        val order = (0..4).map { LineItem("Orange", BigDecimal(".25")) }
        val offerItems = offerService.findOffers(order)
        assertThat(offerItems).containsExactly(LineItem("Buy two oranges, get one free", BigDecimal("-0.25")))
    }

    @Test
    fun `detect buy two oranges get one free twice on orders with six oranges`(){
        val order = (0..5).map { LineItem("Orange", BigDecimal(".25")) }
        val offerItems = offerService.findOffers(order)
        assertThat(offerItems).containsExactly(LineItem("Buy two oranges, get one free", BigDecimal("-0.25")), LineItem("Buy two oranges, get one free", BigDecimal("-0.25")))
    }
}