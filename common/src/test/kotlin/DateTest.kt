import com.comsysto.kotlinfullstack.parseDate
import kotlin.test.assertEquals
import kotlin.test.Test

class DateTest {
    @Test
    fun testParse() {
        val date = parseDate("2017-10-24T13:31:19")
        assertEquals(2017, date.getFullYear())
        assertEquals(9, date.getMonth())
        assertEquals(24, date.getDate())
        assertEquals(13, date.getHours())
        assertEquals(31, date.getMinutes())

    }
}
