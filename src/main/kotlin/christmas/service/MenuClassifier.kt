package christmas.service

import christmas.constants.menu.*

object MenuClassifier {
    private val menuTypeStore = mutableMapOf<String, MenuType>() // 메뉴 이름, 메뉴 종류
    private val menuPriceStore = mutableMapOf<String, Int>() // 메뉴 이름, 메뉴 금액

    init {
        setMenuStore(enumValues<Appetizer>(), MenuType.APPETIZER)
        setMenuStore(enumValues<Main>(), MenuType.MAIN)
        setMenuStore(enumValues<Dessert>(), MenuType.DESSERT)
        setMenuStore(enumValues<Drink>(), MenuType.DRINK)
    }

    private fun setMenuStore(menuItems: Array<out Menu>, menuType: MenuType) {
        menuTypeStore.putAll(menuItems.associate { menu -> menu.menuName to menuType })
        menuPriceStore.putAll(menuItems.associate { menu -> menu.menuName to menu.price })
    }

    fun getMenuType(menuName: String) = menuTypeStore.getOrDefault(menuName, MenuType.NOTHING)

    fun getMenuPrice(menuName: String) = menuPriceStore.getOrDefault(menuName, 0)
}