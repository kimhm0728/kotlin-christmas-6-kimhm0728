package christmas.model

import christmas.constants.menu.MenuType

class OrderMenu(
    private val menuStore: Map<String, Int>,
    private val menuTypeStore: Map<MenuType, Int>,
    private val totalPrice: Int,
    private val totalCount: Int
)