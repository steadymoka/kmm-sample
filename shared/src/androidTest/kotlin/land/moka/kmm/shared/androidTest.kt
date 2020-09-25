package land.moka.kmm.shared

import org.junit.Assert.assertTrue
import org.junit.Test

class GreetingTest {

    @Test
    fun testExample() {
        assertTrue("Check Android is mentioned", Container().platform.contains("Android"))
    }

}
