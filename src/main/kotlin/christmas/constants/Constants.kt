package christmas.constants

import christmas.constants.menu.Drink

enum class Constants(val value: Int) {
    DATE_START(1),
    DATE_END(31),
    ORDER_SIZE(20),
    PRESENT_PRICE(Drink.CHAMPAGNE.price),
    EVENT_STANDARD(10000)
}