package christmas.builder

import christmas.model.store.BadgeStore
import christmas.service.calculator.BadgeCalculator

object BadgeStoreBuilder {
    fun createBadge(totalBenefitPrice: Int): BadgeStore {
        val badge = BadgeCalculator.getBadge(totalBenefitPrice)
        return BadgeStore(badge)
    }
}