package christmas.constants

enum class Benefit(private val msg: String) {
    CHRISTMAS("크리스마스 디데이 할인"),
    WEEKDAY("평일 할인"),
    WEEKEND("주말 할인"),
    SPECIAL("특별 할인"),
    PRESENT("증정 이벤트");

    override fun toString() = msg
}