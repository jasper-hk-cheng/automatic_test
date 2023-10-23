package tw.com.jasper.automatic_test.components

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import timber.log.Timber

class TimberExtension : BeforeAllCallback, AfterAllCallback {
    private val printlnTree = object : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            println("$tag: --> $message")
        }

        override fun createStackElementTag(element: StackTraceElement): String = "(${element.fileName}:${element.lineNumber}) - ${element.methodName}"
    }

    override fun beforeAll(context: ExtensionContext?) {
        Timber.plant(printlnTree)
        Timber.i("### ${TimberExtension::class.simpleName} beforeAll works ...")
    }

    override fun afterAll(context: ExtensionContext?) {
        Timber.i("### ${TimberExtension::class.simpleName} afterAll works ...")
        Timber.uproot(printlnTree)
    }
}