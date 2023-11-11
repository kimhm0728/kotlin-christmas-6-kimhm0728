package christmas.model

import christmas.utils.convertWithDigitComma

class Price(private var price: Int = 0) : Comparable<Price> {
    fun add(price: Int) {
        this.price += price
    }

    fun isEmpty() = price == 0

    operator fun plus(other: Price) = Price(this.price + other.price)

    operator fun minus(other: Price) = Price(this.price - other.price)

    override fun compareTo(other: Price) =
        this.price.compareTo(other.price)

    override fun toString() = price.convertWithDigitComma()
}