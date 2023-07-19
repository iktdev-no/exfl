package no.iktdev.exfl.observable
import junit.framework.TestCase.assertFalse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify
class ObservableMapListenerTest {

    private lateinit var observableMap: ObservableMap<String, Int>
    private lateinit var listener: ObservableMap.Listener<String, Int>

    @Before
    fun setUp() {
        observableMap = ObservableMap()
        listener = Mockito.mock(ObservableMap.Listener::class.java) as ObservableMap.Listener<String, Int>
        observableMap.addListener(listener)

    }
    @Test
    fun testPut() {
        observableMap.put("A", 1)

        Mockito.verify(listener).onPut("A", 1)

        Mockito.verify(listener).onMapUpdated(mutableMapOf("A" to 1))
    }

    @Test
    fun testPutAll() {
        val mapToAdd = mapOf("A" to 1, "B" to 2, "C" to 3)
        observableMap.putAll(mapToAdd)

        Mockito.verify(listener).onMapUpdated(observableMap.toMutableMap())

        assertEquals(3, observableMap.size)
        assertTrue(observableMap.containsKey("A"))
        assertTrue(observableMap.containsKey("B"))
        assertTrue(observableMap.containsKey("C"))
        assertEquals(1, observableMap["A"])
        assertEquals(2, observableMap["B"])
        assertEquals(3, observableMap["C"])
    }

    @Test
    fun testRemove() {
        observableMap.put("A", 1)

        observableMap.remove("A")

        Mockito.verify(listener).onRemove("A", 1)
        Mockito.verify(listener).onMapUpdated(mutableMapOf())

        assertEquals(0, observableMap.size)
        assertFalse(observableMap.containsKey("A"))
    }

    @Test
    fun testClear() {
        observableMap.put("A", 1)
        observableMap.put("B", 2)
        observableMap.put("C", 3)

        observableMap.clear()

        Mockito.verify(listener).onMapUpdated(mutableMapOf())

        assertEquals(0, observableMap.size)
        assertFalse(observableMap.containsKey("A"))
        assertFalse(observableMap.containsKey("B"))
        assertFalse(observableMap.containsKey("C"))
    }
}
