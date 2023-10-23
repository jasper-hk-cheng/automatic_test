package tw.com.jasper.automatic_test.components

import android.app.Instrumentation
import android.content.Context

open class BaseTest {
    protected lateinit var instrumentation: Instrumentation
    protected lateinit var context: Context

    protected fun Int.toText(): String = context.getString(this)
    protected fun Int.toResEntryName(): String = context.resources.getResourceEntryName(this)
}
