package christmas.model

import christmas.constants.menu.MenuType

class OrderMenu(
    private val menuStore: Map<String, Int>,
    private val menuTypeStore: Map<MenuType, Int>,
    val totalPrice: Price
) {
    fun forEach(action: (String, Int) -> Unit) =
        menuStore.forEach { (menu, count) ->
            action(menu, count)
        }

    fun getMenuTypeCount(menuType: MenuType) =
        menuTypeStore.getOrDefault(menuType, 0)
}