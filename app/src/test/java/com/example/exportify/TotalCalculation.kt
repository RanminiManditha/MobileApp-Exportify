import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OrderDetailsTest {

    @Test
    fun testCalculation() {
        // Set the values for testing
        val pricePerUnit = "10"
        val qty = 3

        // Calculate the total
        val total = qty * pricePerUnit.toInt()

        // Verify that the total is calculated correctly
        assertEquals(30, total)
    }
}
