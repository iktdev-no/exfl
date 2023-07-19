package no.iktdev.exfl.observable

class Observables {

    class ObservableValue<T>(val initialValue: T? = null) {
        var value: T? = null
            private set

        fun next(value: T) {
            this.value = value
            listeners.forEach { it.onUpdated(value) }
        }

        private val listeners: MutableList<ValueListener<T>> = mutableListOf()
        fun addListener(listener: ValueListener<T>) {
            this.listeners.add(listener)
        }
        fun removeListener(listener: ValueListener<T>) {
            this.listeners.remove(listener)
        }

        interface ValueListener<T> {
            fun onUpdated(value: T)
        }
    }

    abstract class IObservable {
        abstract val id: String
    }


    class ObservableList<T: IObservable>(private val initalItems: List<T> = emptyList(), private val listener: Listener<T>? = null) {
        var items: MutableList<T> = mutableListOf()
            private set
        var listeners: MutableList<Listener<T>> = mutableListOf()
            private set

        init {
            listener?.let { listeners.add(it) }
            if (initalItems.isNotEmpty()) {
                items.addAll(initalItems)
                listeners.forEach { it.onListChanged(items) }
            }
        }

        fun set(item: T) {
            items.find { it.id == item.id }?.let {
                items.remove(it)
            }
            items.add(item)
            listeners.forEach { it.onChanged(item) }
        }

        fun addAll(items: List<T>) {
            this.items.addAll(items)
            listeners.forEach { it.onListChanged(items) }
        }

        fun addListener(listener: Listener<T>) {
            this.listeners.add(listener)
        }
        fun removeListener(listener: Listener<T>) {
            if (listeners.contains(listener))
                listeners.remove(listener)
            else
                println("Cant remove something that is not added")
        }



        interface Listener<T> {
            fun onChanged(item: T)
            fun onListChanged(items: List<T>)
        }
    }

    class ObservableMap<K, V> : MutableMap<K, V> by mutableMapOf<K, V>() {

        private val listeners: MutableList<Listener<K, V>> = mutableListOf()

        override fun put(key: K, value: V): V? {
            val oldValue = mutableMapOf<K, V>().put(key, value)
            listeners.forEach {
                it.onPut(key, value)
                it.onMapUpdated(this)
            }
            return oldValue
        }

        override fun remove(key: K): V? {
            val value = mutableMapOf<K, V>().remove(key)
            if (value != null) {
                listeners.forEach {
                    it.onRemove(key, value)
                    it.onMapUpdated(this)

                }
            }
            return value
        }

        fun addListener(listener: Listener<K, V>) {
            listeners.add(listener)
        }

        fun removeListener(listener: Listener<K, V>) {
            listeners.remove(listener)
        }

        interface Listener<K, V> {
            fun onPut(key: K, value: V) {}
            fun onRemove(key: K, value: V) {}
            fun onMapUpdated(map: Map<K,V>) {}
        }
    }



}