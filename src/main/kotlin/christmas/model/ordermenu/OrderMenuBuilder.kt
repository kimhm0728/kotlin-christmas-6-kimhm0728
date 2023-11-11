package christmas.model.ordermenu

import christmas.constants.Constants.ORDER_MAX_SIZE
import christmas.constants.menu.MenuType
import christmas.constants.Exception
import christmas.model.Price
import christmas.service.MenuClassifier

class OrderMenuBuilder(inputOrder: String) {
    private val menuStore = mutableMapOf<String, Int>() // 메뉴 이름, 주문 개수
    private val menuTypeStore = mutableMapOf<MenuType, Int>() // 메뉴 종류, 주문 개수
    private var totalPrice = Price()
    private var totalCount = 0

    init {
        composeStoreAndValidateMenu(inputOrder.toOrderMenuItems())
    }

    private fun String.toOrderMenuItems() = this.split(",").map { menu -> menu.split("-") }

    private fun composeStoreAndValidateMenu(menuItems: List<List<String>>) {
        menuItems.forEach { menu ->
            val menuName = menu[0].also { it.validateNameDuplication() }
            val menuCount = menu[1].validateAndConvertMenuCount()
            val menuType = MenuClassifier.getMenuType(menuName).also { it.validateMenuType() }

            updateOrderMenuDate(menuName, menuCount, menuType)
        }

        validateDrinkOnly()
    }

    private fun String.validateNameDuplication() = require(menuStore[this] == null) { Exception.ORDER_MENU }

    private fun String.validateAndConvertMenuCount(): Int {
        val count = this.toIntOrNull()
        require(count != null) { Exception.ORDER_MENU }
        require(count > 0) { Exception.ORDER_MENU }
        require(totalCount + count <= ORDER_MAX_SIZE.value) { Exception.ORDER_MENU_MAX_SIZE }

        return count
    }

    private fun MenuType.validateMenuType() = require(this != MenuType.NOTHING) { Exception.ORDER_MENU }

    private fun validateDrinkOnly() =
        require(menuTypeStore[MenuType.DRINK] != totalCount) { Exception.ORDER_MENU_DRINK_ONLY }

    private fun updateOrderMenuDate(menuName: String, menuCount: Int, menuType: MenuType) {
        totalPrice.add(MenuClassifier.getMenuPrice(menuName) * menuCount)
        totalCount += menuCount
        menuStore[menuName] = menuStore.getOrDefault(menuName, 0) + menuCount
        menuTypeStore[menuType] = menuTypeStore.getOrDefault(menuType, 0) + menuCount
    }

    fun create() = OrderMenu(menuStore, menuTypeStore, totalPrice)
}