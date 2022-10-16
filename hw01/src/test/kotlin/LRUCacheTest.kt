import org.junit.Test
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals

import java.util.*

class LRUCacheTest {

    @Test
    fun `test empty cache`() {
        val cache: LRUCache<String, Int> = LRUCacheImpl(SMALL_CAP)

        assertEquals(cache.size(), 0)
        assertFalse(cache[KEY1].isPresent)
        assertFalse(cache[KEY2].isPresent)
        assertFalse(cache[KEY3].isPresent)
    }

    @Test
    fun `test get and set 1 value`() {
        val cache: LRUCache<String, Int> = LRUCacheImpl(SMALL_CAP)

        cache[KEY1] = VAL1

        assertTrue(cache.size() <= SMALL_CAP)
        assertEquals(Optional.of(VAL1), cache[KEY1])
    }

    @Test
    fun `test set 2 values`() {
        val cache: LRUCache<String, Int> = LRUCacheImpl(SMALL_CAP)

        cache[KEY1] = VAL1
        cache[KEY1] = VAL2

        assertTrue(cache.size() <= SMALL_CAP)
        assertEquals(Optional.of(VAL2), cache[KEY1])
    }

    @Test
    fun `test remove`() {
        val cache: LRUCache<String, Int> = LRUCacheImpl(BIG_CAP)

        cache[KEY1] = VAL1
        cache[KEY2] = VAL2
        cache[KEY3] = VAL3

        assertTrue(cache.size() <= BIG_CAP)
        assertFalse(cache[KEY1].isPresent)
        assertEquals(Optional.of(VAL2), cache[KEY2])
        assertEquals(Optional.of(VAL3), cache[KEY3])
    }

    @Test
    fun `test remove when set`() {
        val cache: LRUCache<String, Int> = LRUCacheImpl(BIG_CAP)

        cache[KEY1] = VAL1
        cache[KEY2] = VAL2
        cache[KEY1] = VAL3
        cache[KEY3] = VAL4

        assertTrue(cache.size() <= BIG_CAP)
        assertEquals(Optional.of(VAL3), cache[KEY1])
        assertFalse(cache[KEY2].isPresent)
        assertEquals(Optional.of(VAL4), cache[KEY3])
    }

    @Test
    fun `test remove when get`() {
        val cache: LRUCache<String, Int> = LRUCacheImpl(BIG_CAP)

        cache[KEY1] = VAL1
        cache[KEY2] = VAL2
        cache[KEY1]
        cache[KEY3] = VAL3

        assertTrue(cache.size() <= BIG_CAP)
        assertEquals(Optional.of(VAL1), cache[KEY1])
        assertFalse(cache[KEY2].isPresent)
        assertEquals(Optional.of(VAL3), cache[KEY3])
    }

    companion object {
        private const val SMALL_CAP = 1
        private const val BIG_CAP = 2

        private const val KEY1 = "KEY1"
        private const val KEY2 = "KEY2"
        private const val KEY3 = "KEY3"

        private const val VAL1 = 1
        private const val VAL2 = 2
        private const val VAL3 = 3
        private const val VAL4 = 4
    }
}