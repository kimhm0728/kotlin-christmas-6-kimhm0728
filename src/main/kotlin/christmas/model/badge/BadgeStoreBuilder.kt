package christmas.model.badge

import christmas.model.Price

object BadgeStoreBuilder {
    fun create(totalBenefitPrice: Price): BadgeStore {
        val badge = BadgeCalculator.getBadge(totalBenefitPrice)
        return BadgeStore(badge)
    }
}