package tw.com.jasper.automatic_test

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import tw.com.jasper.automatic_test.components.BaseTest
import tw.com.jasper.automatic_test.components.TimberExtension

@ExtendWith(TimberExtension::class)
class TestInstrumentationApi : BaseTest() {

    @BeforeEach
    fun setUp() {
        with(InstrumentationRegistry.getInstrumentation()) {
            super.instrumentation = this
            super.context = targetContext
        }
    }
}
