package com.app.offer

import com.app.order.LineItem
import java.math.BigDecimal

/**
 * Abstract class representing an offer.
 */
sealed class Offer(val description: String) {

    /**
     * Checks if the list of items would qualify for this offer. A list of LineItems is returned representing the offers
     * this list of items qualify for.
     * @param items List of LineItems to check this offer against.
     * @return LineItems representing the savings for this offer if any items qualified. Empty list if none qualified.
     */
    abstract fun check(items: List<LineItem>) : List<LineItem>

    companion object {
        val NEGATIVE_ONE = BigDecimal("-1")
    }
}

/**
 * Offer for buy one apple, get one free
 */
class AppleBogoOffer : Offer("Buy one apple, get one free") {

    override fun check(items: List<LineItem>): List<LineItem> {
        val apples = items.filter { it.item.equals("apple", true) }
        val offers = apples.count() / 2
        return (1..offers).map { LineItem(description, NEGATIVE_ONE * apples.first().price) }
    }
}

/**
 * Offer for by two oranges, get one free
 */
class OrangeThreeForTwoOffer : Offer("Buy two oranges, get one free") {

    override fun check(items: List<LineItem>): List<LineItem> {
        val oranges = items.filter { it.item.equals("orange", true) }
        val offers = oranges.count() / 3
        return (1..offers).map { LineItem(description, NEGATIVE_ONE * oranges.first().price) }
    }
}