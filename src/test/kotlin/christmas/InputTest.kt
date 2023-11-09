package christmas

import christmas.constants.Exception
import christmas.model.VisitDate
import christmas.view.input.InputConverter
import christmas.view.input.InputValidator
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class InputTest {
    private val validator = InputValidator
    private val converter = InputConverter

    @Test
    fun `방문할 날짜에 아무 값도 입력하지 않으면 예외가 발생한다`() {
        Assertions.assertThatThrownBy { validator.checkVisitDate("") }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.VISIT_DATE.toString())
    }

    @ParameterizedTest
    @ValueSource(strings = ["9000000000", " ", "10 ", "abc"])
    fun `방문할 날짜에 문자나 INT의 범위에 벗어나는 숫자를 입력하면 예외가 발생한다`(inputDate: String) {
        Assertions.assertThatThrownBy { validator.checkVisitDate(inputDate) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Exception.VISIT_DATE.toString())
    }

    @ParameterizedTest
    @ValueSource(strings = ["10", "1", "31"])
    fun `방문한 날짜를 올바르게 입력하면 예외가 발생하지 않는다`(inputDate: String) {
        Assertions.assertThatCode { validator.checkVisitDate(inputDate) }
            .doesNotThrowAnyException()
    }

    @Test
    fun `입력 문자열을 VisitDate 객체로 변환한다`() {
        // given
        val case = "20"

        // when
        val result = converter.convertToVisitDate(case)

        // then
        assertThat(result).isInstanceOf(VisitDate::class.java)
    }
}