package christmas.model.badge

import christmas.constants.Badge
import christmas.model.Price

object BadgeCalculator {
    fun getBadge(totalBenefitPrice: Price): Badge {
        Badge.entries.forEach { badge ->
            if (totalBenefitPrice >= badge.minimumPrice) {
                return badge
            }
        }
        return Badge.NO_BADGE
    }
}