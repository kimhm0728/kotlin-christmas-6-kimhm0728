package christmas.constants

import christmas.constants.menu.Drink
import christmas.model.Price

enum class Constants(val value: Int) {
    DATE_START(1),
    DATE_END(31),
    ORDER_MAX_SIZE(20),
    CHRISTMAS_DATE(25),
}

enum class PriceConstants(val price: Price) {
    PRESENT_PRICE(Price(Drink.CHAMPAGNE.price)),
    NO_PRESENT_PRICE(Price()),
    EVENT_STANDARD(Price(10000)),
    PRESENT_STANDARD(Price(120000)),
}