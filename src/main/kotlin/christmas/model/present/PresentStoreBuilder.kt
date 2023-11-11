package christmas.model.present

import christmas.model.Price

object PresentStoreBuilder {
    private val calculator = PresentCalculator

    fun create(totalOrderPrice: Price): PresentStore {
        val presentPrice =
            if (calculator.isPresent(totalOrderPrice)) calculator.getPresent()
            else calculator.getNoPresent()
        return PresentStore(presentPrice)
    }
}