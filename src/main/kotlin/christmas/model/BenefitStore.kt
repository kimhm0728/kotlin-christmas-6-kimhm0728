package christmas.model

import christmas.model.badge.BadgeStore
import christmas.model.discount.DiscountStore
import christmas.model.present.PresentStore

class BenefitStore(
    val discountStore: DiscountStore,
    val presentStore: PresentStore,
    val badgeStore: BadgeStore
)