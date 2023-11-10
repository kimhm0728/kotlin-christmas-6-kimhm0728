package christmas.constants

enum class Print(private val msg: String) {
    NO_BENEFIT("없음"),
    PRESENT_MENU("샴페인 1개");

    override fun toString() = msg
}