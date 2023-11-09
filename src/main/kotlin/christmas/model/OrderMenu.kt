package christmas.model

import christmas.constants.menu.MenuType

class OrderMenu(
    val menuStore: Map<String, Int>,
    val menuTypeStore: Map<MenuType, Int>,
    val totalPrice: Int,
    val totalCount: Int
)