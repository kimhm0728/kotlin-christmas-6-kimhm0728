package christmas.service

import christmas.constants.Badge

object BadgeApplier {
    fun getBadge(totalBenefitPrice: Int): Badge {
        Badge.entries.forEach { badge ->
            if (totalBenefitPrice >= badge.minimumPrice) {
                return badge
            }
        }
        return Badge.NO_BADGE
    }
}