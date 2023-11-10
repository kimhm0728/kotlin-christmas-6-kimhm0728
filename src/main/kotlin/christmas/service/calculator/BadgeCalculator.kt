package christmas.service.calculator

import christmas.constants.Badge

object BadgeCalculator {
    fun getBadge(totalBenefitPrice: Int): Badge {
        Badge.entries.forEach { badge ->
            if (totalBenefitPrice >= badge.minimumPrice) {
                return badge
            }
        }
        return Badge.NO_BADGE
    }
}