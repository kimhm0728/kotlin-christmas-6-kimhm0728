package christmas.service

import christmas.constants.Constants.ORDER_SIZE
import christmas.constants.menu.MenuType
import christmas.constants.Exception
import christmas.model.OrderMenu

class OrderMenuGenerator(inputOrder: String) {
    private val menuStore = mutableMapOf<String, Int>()
    private val menuTypeStore = mutableMapOf<MenuType, Int>()
    private var totalPrice = 0
    private var totalCount = 0

    init {
        composeStoreAndValidate(inputOrder.split(",").map { it.split("-") })
    }

    private fun composeStoreAndValidate(splitMenus: List<List<String>>) {
        splitMenus.forEach { menu ->
            val menuName = menu[0].also { it.validateName() }
            val menuCount = menu[1].validateAndReturnCount()
            val menuType = MenuClassifier.getMenuType(menuName).also { it.validateType() }

            totalPrice += MenuClassifier.getMenuPrice(menuName) * menuCount
            totalCount += menuCount
            menuStore[menuName] = menuStore.getOrDefault(menuName, 0) + menuCount
            menuTypeStore[menuType] = menuTypeStore.getOrDefault(menuType, 0) + menuCount
        }

        validateDrinkCount()
    }

    private fun String.validateName() = require(menuStore[this] == null) { Exception.ORDER_MENU }

    private fun String.validateAndReturnCount(): Int {
        val count = this.toIntOrNull()
        require(count != null) { Exception.ORDER_MENU }
        require(count > 0) { Exception.ORDER_MENU }
        require(totalCount + count <= ORDER_SIZE.value) { Exception.ORDER_MENU_MAX_SIZE }
        return count
    }

    private fun MenuType.validateType() = require(this != MenuType.NOTHING) { Exception.ORDER_MENU }

    private fun validateDrinkCount() = require(menuTypeStore[MenuType.DRINK] != totalCount) { Exception.ORDER_MENU_DRINK_ONLY }

    fun createOrderMenu() = OrderMenu(menuStore, menuTypeStore, totalPrice, totalCount)
}