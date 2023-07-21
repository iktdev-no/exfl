package no.iktdev.exfl.observable

class ObservableMap<K, V>(private val map: MutableMap<K, V> = mutableMapOf()) : MutableMap<K, V> {

    private val listeners: MutableList<Listener<K, V>> = mutableListOf()
    override val size: Int
        get() = map.size

    override fun containsKey(key: K): Boolean = map.containsKey(key)
    override fun containsValue(value: V): Boolean = map.containsValue(value)
    override fun get(key: K): V? = map[key]

    override fun isEmpty(): Boolean = map.isEmpty()
    override fun clear() {
        map.clear()
        listeners.forEach { it.onMapUpdated(map.toMap()) }
    }

    override fun put(key: K, value: V): V? {
        val oldValue = map.put(key, value)
        listeners.forEach {
            it.onPut(key, value)
            it.onMapUpdated(map.toMap())
        }
        return oldValue
    }

    override fun putAll(from: Map<out K, V>) {
        map.putAll(from)
        listeners.forEach { it.onMapUpdated(map.toMap()) }
    }

    override fun remove(key: K): V? {
        val value = map.remove(key)
        if (value != null) {
            listeners.forEach {
                it.onRemove(key, value)
                it.onMapUpdated(map.toMutableMap())
            }
        }
        return value
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = map.entries

    override val keys: MutableSet<K>
        get() = map.keys

    override val values: MutableCollection<V>
        get() = map.values

    fun addListener(listener: Listener<K, V>) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener<K, V>) {
        listeners.remove(listener)
    }

    interface Listener<K, V> {
        fun onPut(key: K, value: V) {}
        fun onRemove(key: K, value: V) {}
        fun onMapUpdated(map: Map<K, V>) {}
    }
}
fun <K, V> observableMapOf(vararg pairs: Pair<K, V>): ObservableMap<K, V> {
    val observableMap = ObservableMap<K, V>()
    observableMap.putAll(pairs)
    return observableMap
}