package christmas.builder

import christmas.model.OrderMenu
import christmas.model.VisitDate
import christmas.model.store.BenefitStore

object BenefitStoreBuilder {
    fun createBenefitStore(visitDate: VisitDate, orderMenu: OrderMenu): BenefitStore {
        val discountStore = DiscountStoreBuilder(visitDate, orderMenu).createDiscountStore()
        val presentStore = PresentStoreBuilder.createPresentStore(discountStore.totalPrice)
        val badgeStore = BadgeStoreBuilder.createBadge(discountStore.totalPrice + presentStore.price)

        return BenefitStore(discountStore, presentStore, badgeStore, orderMenu.totalPrice)
    }
}