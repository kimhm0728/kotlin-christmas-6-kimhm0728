package christmas.constants

enum class Exception(private val msg: String) {
    VISIT_DATE("유효하지 않은 날짜입니다."),
    ORDER_MENU("유효하지 않은 주문입니다."),
    ORDER_MENU_DRINK_ONLY("음료만 주문할 수 없습니다."),
    ORDER_MENU_MAX_SIZE("최대 20개까지만 주문할 수 있습니다.");

    override fun toString() = "$ERROR_MSG $msg $RE_INPUT"

    companion object {
        private const val ERROR_MSG = "[ERROR]"
        private const val RE_INPUT = "다시 입력해 주세요."
    }
}