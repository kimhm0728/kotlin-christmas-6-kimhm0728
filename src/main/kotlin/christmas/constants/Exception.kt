package christmas.constants

enum class Exception(private val msg: String) {
    VISIT_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요.");

    override fun toString() = "$ERROR_MSG $msg"

    companion object {
        private const val ERROR_MSG = "[ERROR]"
    }
}