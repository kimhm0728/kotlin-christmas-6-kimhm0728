package christmas.view.output

import christmas.model.OrderMenu

object OuputView {
    fun printStartGreeting() {
        println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.")
    }

    fun printEventPreviewInfo() {
        println("12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!")
    }

    fun printOrderMenus(orderMenu: OrderMenu) {
        println()
        println("<주문 메뉴>")
        orderMenu.menuStore.forEach { menu ->
            println("${menu.key} ${menu.value}개")
        }
    }

    fun printTotalPriceBeforeDiscount(orderMenu: OrderMenu) {
        println()
        println("<할인 전 총주문 금액>")
        println("${orderMenu.totalPrice}원")
    }
}