package tw.com.jasper.automatic_test.junit5api

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assumptions.assumeFalse
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.Assumptions.assumingThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import timber.log.Timber
import tw.com.jasper.automatic_test.junit5api.utils.TimberExtension
import java.util.stream.Stream

@ExtendWith(TimberExtension::class)
@DisplayName("JUnit5 API Tester")
class TestBasicApi {

    companion object {
        @JvmStatic
        @BeforeAll
        fun setupClazz() {
            Timber.i("before all...")
        }

        @JvmStatic
        @AfterAll
        fun tearDownClazz() {
            Timber.i("after all...")
        }
    }

    @BeforeEach
    fun setUp() {
        Timber.i("before each...")
    }

    @AfterEach
    fun tearDown() {
        Timber.i("after each...")
    }

    // --- --- --- --- --- --- --- --- --- --- --- ---

    @Test
    @DisplayName("JUnit5基本款")
    fun testJUnit5PrimaryFeature() {
        Timber.i("JUnit5 Primary Feature")
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3])
    @DisplayName("測試 @ParameterizedTest 參數為整數 1 2 3")
    fun testParameterizedInputValue(value: Int) {
        Timber.i("parameterized value: $value")
    }

    @RepeatedTest(3)
    @DisplayName("測試 @RepeatedTest 反覆3次")
    fun testRepeatedExecution(repetitionInfo: RepetitionInfo) {
        Timber.i("repetition current count: ${repetitionInfo.currentRepetition}")
        Timber.i("repetition total count: ${repetitionInfo.totalRepetitions}")
    }

    // --- update docs

    @Test
    fun testAssertThrowException() {
        val errMsg = "Not supported"
        val throwable: Throwable = assertThrows(UnsupportedOperationException::class.java) {
            throw UnsupportedOperationException(errMsg)
        }
        assertEquals(errMsg, throwable.message)
    }

    @Test
    fun testLambdaExpressions() {
        val numbers = listOf("1", "2", "3")
        assertTrue(numbers.stream().mapToInt(Integer::parseInt).sum() > 5) {
            "不符預期，應該要大於5。"
        }
    }

    @Test
    fun testGroupAssertions() {
        val numbers = intArrayOf(0, 1, 2, 3, 4)
        assertAll("verify the element of the int array !!", {
            assertEquals(numbers[0], 0)
        }, {
            assertEquals(numbers[3], 3)
        })
    }

    @Test
    fun testTrueAssumption() {
        assumeTrue(5 > 1)
        assertEquals(5 + 2, 7)
    }

    @Test
    fun testFalseAssumption() {
        assumeFalse(5 < 1)
        assertEquals(5 + 2, 7)
    }

    @Test
    fun testAssumingThat() {
        val someString = "Just a string"
        val expectedString = "Just a string"
        assumingThat(someString == expectedString) {
            assertEquals(2 + 2, 4) // assumption若為false, 這裡assertion會跳過 視同通過測試
        }
    }

    private val originalStringList = listOf("A", "B", "C")
    private val translatedStringList = listOf("AA", "BB", "CC")
    private fun String.translate(): String = "$this$this"

    @TestFactory
    fun testDynamicGenerateCase(): Stream<DynamicTest> = originalStringList.stream().map { text ->
        dynamicTest("動態生成測案1_目前測資為_$text") {
            val index = originalStringList.indexOf(text)
            assertEquals(translatedStringList[index], text.translate())
        }
    }

    @TestFactory
    fun testDynamicContainerStream(): Stream<DynamicContainer> = originalStringList.stream().map { text ->
        val verifyRange = Executable {
            assertTrue(text.single().code in 65..67)
        }
        val verifyValue = Executable {
            val index = originalStringList.indexOf(text)
            assertEquals("A".single().code + index, text.single().code)
        }
        val dynamicTestStream: Stream<DynamicTest> = listOf<DynamicTest>(
            dynamicTest("測其ascii code是否在合理範圍內", verifyRange),
            dynamicTest("測其ascii code是否符合預期的值", verifyValue),
        ).stream()
        dynamicContainer(
            "outer container - 目前測試 $text", Stream.of(
                dynamicContainer("inner container - 目前測試 $text", dynamicTestStream)
            )
        )
    }
}
