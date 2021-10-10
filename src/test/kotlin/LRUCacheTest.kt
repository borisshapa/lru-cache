import lrucache.LRUCache
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LRUCacheTest {

    @Test
    fun putGet() {
        val cache = LRUCache<Int, Int>(100)
        cache.put(1, 21)
        cache.put(2, 22)

        assertEquals(2, cache.size())
        assertEquals(21, cache.get(1))
        assertEquals(22, cache.get(2))
    }
    
    @Test
    fun sizeLimitation() {
        val cache = LRUCache<Int, Int>(1)
        cache.put(1, 21)
        cache.put(2, 22)

        assertEquals(1, cache.size())
        assertEquals(22, cache.get(2))
        assertNull(cache.get(1))
    }
    
    @Test
    fun getNonexistent() {
        val cache = LRUCache<Int, Int>(1)

        assertNull(cache.get(12))
    }
    
    @Test 
    fun maxSizeEqualToZero() {
        val cache = LRUCache<Int, Int>(0)
        for (i in 0 until 4) {
            cache.put(i, i)
        }

        assertEquals(0, cache.size())
        assertNull(cache.get(1))
        assertNull(cache.get(2))
        assertNull(cache.get(3))
    }
    
    @Test
    fun increaseLifetimeOfElementAfterGetting() {
        val cache = LRUCache<String, Int>(3)
        
        cache.put("a", 1)
        cache.put("b", 2)
        cache.put("c", 3)
        
        cache.get("a")
        cache.put("d", 4)
        
        assertEquals(3, cache.size())
        assertEquals(1, cache.get("a"))
        assertNull(cache.get("b"))
        assertEquals(3, cache.get("c"))
        assertEquals(4, cache.get("d"))
    }
    
    @Test
    fun remove() {
        val cache = LRUCache<String, String>(3)
        cache.put("a", "alive")
        cache.put("d", "died")
        cache.remove("d")
        
        assertEquals(1, cache.size())
        assertEquals("alive", cache.get("a"))
        assertNull(cache.get("d"))
    }
    
    @Test
    fun removeNonexistent() {
        val cache = LRUCache<String, Int>(3)
        cache.put("a", 1)
        cache.remove("b")
        
        assertEquals(1, cache.size())
        assertEquals(1, cache.get("a"))
    }

    @Test
    fun clear() {
        val cache = LRUCache<Int, Int>(3)
        for (i in 0 until 4) {
            cache.put(i, i)
        }
        
        cache.clear()
        assertEquals(0, cache.size())
        assertNull(cache.get(0))
        assertNull(cache.get(1))
        assertNull(cache.get(2))
        assertNull(cache.get(3))
    }
}