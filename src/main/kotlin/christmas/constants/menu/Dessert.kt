package christmas.constants.menu

enum class Dessert(override val menuName: String, override val price: Int) : Menu {
    CHOCOLATE_CAKE("초코케이크", 15000),
    ICE_CREAM("아이스크림", 5000)
}