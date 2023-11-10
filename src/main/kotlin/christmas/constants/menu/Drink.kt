package christmas.constants.menu

enum class Drink(override val menuName: String, override val price: Int) : Menu {
    ZERO_COLA("제로콜라", 3000),
    RED_WINE("레드와인", 60000),
    CHAMPAGNE("샴페인", 25000)
}