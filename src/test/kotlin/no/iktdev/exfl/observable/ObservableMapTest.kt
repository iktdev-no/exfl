package no.iktdev.exfl.observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify

class ObservableMapTest {

    private lateinit var observableMap: ObservableMap<String, Int>
    private lateinit var listener: ObservableMap.Listener<String, Int>


    @Before
    fun setUp() {
        observableMap = ObservableMap()
        listener = object : ObservableMap.Listener<String, Int> {
            override fun onPut(key: String, value: Int) {
                println("onPut: Key = $key, Value = $value")
            }

            override fun onRemove(key: String, value: Int) {
                println("onRemove: Key = $key, Value = $value")
            }

            override fun onMapUpdated(map: MutableMap<String, Int>) {
                println("onMapUpdated: $map")
            }
        }
        observableMap.addListener(listener)
    }

    @Test
    fun testPut() {
        observableMap.put("A", 1)

        assertEquals(1, observableMap["A"])
        assertTrue(observableMap.containsKey("A"))
        assertTrue(observableMap.containsValue(1))
    }

    @Test
    fun testPutAll() {
        val mapToAdd = mapOf("A" to 1, "B" to 2, "C" to 3)
        observableMap.putAll(mapToAdd)

        assertEquals(3, observableMap.size)
        assertEquals(1, observableMap["A"])
        assertEquals(2, observableMap["B"])
        assertEquals(3, observableMap["C"])
    }

    @Test
    fun testRemove() {
        observableMap.put("A", 1)

        val removedValue = observableMap.remove("A")

        assertEquals(1, removedValue)
        assertNull(observableMap["A"])
        assertEquals(0, observableMap.size)
        assertTrue(observableMap.isEmpty())
    }

    @Test
    fun testClear() {
        observableMap.put("A", 1)
        observableMap.put("B", 2)
        observableMap.put("C", 3)

        observableMap.clear()

        assertEquals(0, observableMap.size)
        assertTrue(observableMap.isEmpty())
        assertNull(observableMap["A"])
        assertNull(observableMap["B"])
        assertNull(observableMap["C"])
    }
}

