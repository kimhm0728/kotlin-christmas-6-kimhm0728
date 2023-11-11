package christmas.constants

import christmas.model.Price

enum class Badge(private val badgeName: String, val minimumPrice: Price) {
    SANTA("산타", Price(20000)),
    TREE("트리", Price(10000)),
    STAR("별", Price(5000)),
    NO_BADGE("없음", Price(0));

    override fun toString() = badgeName
}