package com.app.offer

import com.app.order.LineItem

/**
 * Service to find qualifying offers for an order
 */
interface OfferService {

    /**
     * Finds offers for the given [items] list if any qualifying offers are found.
     * @param items List of LineItems which are scanned for qualifying offers.
     * @return List of LineItems representing the qualifying offers found from all available offers.
     */
    fun findOffers(items: List<LineItem>) : List<LineItem>

}

/**
 * Default implementation of OfferService. Searches for AppleBogoOffer and OrangeThreeForTwoOffer.
 */
class DefaultOfferService : OfferService{

    private val offers = listOf(AppleBogoOffer(), OrangeThreeForTwoOffer())

    override fun findOffers(items: List<LineItem>): List<LineItem> = offers.flatMap { it.check(items) }

}