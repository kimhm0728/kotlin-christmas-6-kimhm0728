package christmas.service

import christmas.constants.menu.*

object MenuClassifier {
    private val menuTypeStore = mutableMapOf<String, MenuType>()
    private val menuPriceStore = mutableMapOf<String, Int>()

    init {
        menuTypeStore.putAll(Appetizer.entries.associate { menu -> menu.menuName to MenuType.APPETIZER })
        menuTypeStore.putAll(Main.entries.associate { menu -> menu.menuName to MenuType.MAIN })
        menuTypeStore.putAll(Dessert.entries.associate { menu -> menu.menuName to MenuType.DESSERT })
        menuTypeStore.putAll(Drink.entries.associate { menu -> menu.menuName to MenuType.DRINK })

        menuPriceStore.putAll(Appetizer.entries.associate { menu -> menu.menuName to menu.price })
        menuPriceStore.putAll(Main.entries.associate { menu -> menu.menuName to menu.price })
        menuPriceStore.putAll(Dessert.entries.associate { menu -> menu.menuName to menu.price })
        menuPriceStore.putAll(Drink.entries.associate { menu -> menu.menuName to menu.price })
    }

    fun getMenuType(menuName: String) = menuTypeStore.getOrDefault(menuName, MenuType.NOTHING)

    fun getMenuPrice(menuName: String) = menuPriceStore.getOrDefault(menuName, 0)
}