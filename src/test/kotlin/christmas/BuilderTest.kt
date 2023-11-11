package christmas

import christmas.constants.Badge
import christmas.constants.PriceConstants
import christmas.constants.menu.MenuType
import christmas.model.Price
import christmas.model.VisitDate
import christmas.model.badge.BadgeStore
import christmas.model.badge.BadgeStoreBuilder
import christmas.model.discount.DiscountStore
import christmas.model.discount.DiscountStoreBuilder
import christmas.model.ordermenu.OrderMenuBuilder
import christmas.model.present.PresentStore
import christmas.model.present.PresentStoreBuilder
import christmas.utils.convertWithDigitComma
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class BuilderTest {

    @ParameterizedTest
    @MethodSource("배지 부여에 대한 테스트 데이터")
    fun `총 혜택 금액에 따라 배지를 부여한다`(totalBenefitPrice: Int, expected: Badge) {
        // given
        val price = Price(totalBenefitPrice)

        // when
        val result = BadgeStoreBuilder.create(price)

        // then
        assertThat(result).isInstanceOf(BadgeStore::class.java)
        assertThat(result.toString()).isEqualTo(expected.toString())
    }

    @ParameterizedTest
    @MethodSource("증정 이벤트에 대한 테스트 데이터")
    fun `총 주문 금액에 따라 샴페인을 증정한다`(totalOrderPrice: Int, expected: Price) {
        // given
        val price = Price(totalOrderPrice)

        // when
        val result = PresentStoreBuilder.create(price)

        // then
        assertThat(result).isInstanceOf(PresentStore::class.java)
        assertThat(result.price).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("할인 적용에 대한 테스트 데이터")
    fun `방문 날짜에 따라 할인을 적용한다`(date: Int, menuItems: String, expectedDiscounts: List<String>, expectedEmpty: Boolean) {
        // given
        val visitDate = VisitDate(date)
        val orderMenu = OrderMenuBuilder(menuItems).create()

        // when
        val result = DiscountStoreBuilder(visitDate, orderMenu).create()

        // then
        assertThat(result).isInstanceOf(DiscountStore::class.java)

        val actualDiscount = StringBuilder()
        result.forEach { benefit, price -> actualDiscount.append("$benefit $price") }

        expectedDiscounts.forEach { expectedDiscount ->
            assertThat(actualDiscount).contains(expectedDiscount)
        }

        assertThat(result.isEmpty()).isEqualTo(expectedEmpty)
    }

    @ParameterizedTest
    @MethodSource("메뉴 종류 개수에 대한 테스트 데이터")
    fun `주문한 메뉴의 종류 개수를 구한다`(menuType: MenuType, expected: Int) {
        // given
        val menuItems = "양송이수프-2,시저샐러드-1,티본스테이크-1,바비큐립-1,초코케이크-3,제로콜라-1,샴페인-1"

        // when
        val result = OrderMenuBuilder(menuItems).create().getMenuTypeCount(menuType)

        // then
        assertThat(result).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("주문 메뉴 총 금액에 대한 테스트 데이터")
    fun `주문한 메뉴의 총 금액을 구한다`(menuItems: String, expected: Int) {
        // given

        // when
        val result = OrderMenuBuilder(menuItems).create().totalPrice

        // then
        assertThat(result.toString()).isEqualTo(expected.convertWithDigitComma())
    }

    companion object {
        @JvmStatic
        fun `배지 부여에 대한 테스트 데이터`() = listOf(
            Arguments.of(3000, Badge.NO_BADGE),
            Arguments.of(6000, Badge.STAR),
            Arguments.of(10000, Badge.TREE),
            Arguments.of(25000, Badge.SANTA)
        )

        @JvmStatic
        fun `증정 이벤트에 대한 테스트 데이터`() = listOf(
            Arguments.of(120000, PriceConstants.PRESENT_PRICE.price),
            Arguments.of(125000, PriceConstants.PRESENT_PRICE.price),
            Arguments.of(5000, PriceConstants.NO_PRESENT_PRICE.price)
        )

        @JvmStatic
        fun `할인 적용에 대한 테스트 데이터`() = listOf(
            Arguments.of(
                17,
                "시저샐러드-1,바비큐립-1,아이스크림-2",
                listOf("크리스마스 디데이 할인 2,600", "평일 할인 4,046", "특별 할인 1,000"),
                false
            ),
            Arguments.of(30, "티본스테이크-1,초코케이크-2", listOf("주말 할인 2,023"), false),
            Arguments.of(25, "양송이수프-1,제로콜라-1", listOf(""), true)
        )

        @JvmStatic
        fun `메뉴 종류 개수에 대한 테스트 데이터`() = listOf(
            Arguments.of(MenuType.APPETIZER, 3),
            Arguments.of(MenuType.MAIN, 2),
            Arguments.of(MenuType.DESSERT, 3),
            Arguments.of(MenuType.DRINK, 2),
        )

        @JvmStatic
        fun `주문 메뉴 총 금액에 대한 테스트 데이터`() = listOf(
            Arguments.of("양송이수프-1,티본스테이크-1,아이스크림-1", 66000),
            Arguments.of("바비큐립-1,레드와인-1", 114000),
            Arguments.of("시저샐러드-2,해산물파스타-1,초코케이크-3", 96000),
        )
    }
}