package christmas.builder

import christmas.model.store.PresentStore
import christmas.service.calculator.PresentCalculator

object PresentStoreBuilder {
    fun createPresentStore(totalOrderPrice: Int): PresentStore {
        val presentPrice =
            if (PresentCalculator.availablePresent(totalOrderPrice)) PresentCalculator.getPresent()
            else 0
        return PresentStore(presentPrice)
    }
}