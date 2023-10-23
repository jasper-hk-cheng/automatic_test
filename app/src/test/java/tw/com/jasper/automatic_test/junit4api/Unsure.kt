package tw.com.jasper.automatic_test.junit4api

import org.junit.Test
import timber.log.Timber
import tw.com.jasper.automatic_test.junit4api.utils.BaseTest

class Unsure : BaseTest() {

    @Test
    fun test01() {
        Timber.i("test01")
    }

    @Test
    fun test02() {
        Timber.i("test02")
    }
}
