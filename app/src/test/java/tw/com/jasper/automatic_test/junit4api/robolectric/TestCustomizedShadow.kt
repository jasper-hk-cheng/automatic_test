package tw.com.jasper.automatic_test.junit4api.robolectric

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements

class Person {
    fun sayHello(): String = "I'm myself!"
}

@Implements(Person::class)
class ShadowPerson {
    @Implementation
    fun sayHello(): String = "I'm shadow!"
}

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowPerson::class], manifest = Config.NONE)
class ShadowTester {

    private lateinit var person: Person

    @Before
    fun setup() {
        person = Person()
    }

    @Test
    fun testSayHello() {
        assertEquals("I'm shadow!", person.sayHello())
    }
}