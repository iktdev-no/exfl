package no.iktdev.exfl.observable

class ObservableList<T>(private val list: MutableList<T> = mutableListOf()): MutableList<T> {
    private var listeners: MutableList<Listener<T>> = mutableListOf()



    fun addAll(items: List<T>) {
        this.list.addAll(items)
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

    override val size: Int
        get() = list.size



    override fun clear() {
        list.clear()
        listeners.forEach { it.onListChanged(list.toList())}
    }

    override fun addAll(elements: Collection<T>): Boolean {
        listeners.forEach { it.onListChanged(elements.toList()) }
        return list.addAll(elements)
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        listeners.forEach { it.onListChanged(elements.toList()) }
        return list.addAll(index, elements)
    }

    override fun add(index: Int, element: T) {
        listeners.forEach { it.onAdded(element) }
        return list.add(index, element)
    }

    override fun add(element: T): Boolean {
        listeners.forEach { it.onAdded(element) }
        return list.add(element)
    }

    override fun get(index: Int): T {
        return list.get(index)
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    override fun iterator(): MutableIterator<T> {
        return list.iterator()
    }

    override fun listIterator(): MutableListIterator<T> {
        return list.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<T> {
        return list.listIterator(index)
    }

    override fun removeAt(index: Int): T {
        val removed = list.removeAt(index)
        listeners.forEach { it.onRemoved(removed)}
        return removed
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return list.subList(fromIndex, toIndex)
    }

    override fun set(index: Int, element: T): T {
        listeners.forEach { it.onAdded(element) }
        return list.set(index, element)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        return list.retainAll(elements)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        listeners?.forEach { it.onListChanged(list.toList())}
        return list.removeAll(elements)
    }

    override fun remove(element: T): Boolean {
        listeners.forEach { it.onRemoved(element) }
        return list.remove(element)
    }

    override fun lastIndexOf(element: T): Int {
        return list.lastIndexOf(element)
    }

    override fun indexOf(element: T): Int {
        return list.indexOf(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return list.containsAll(elements)
    }

    override fun contains(element: T): Boolean {
        return list.contains(element)
    }


    interface Listener<T> {
        fun onAdded(item: T) {}
        fun onRemoved(item: T) {}
        fun onListChanged(items: List<T>) {}
    }
}

fun <T> observableListOf(vararg items: T): ObservableList<T> {
    val list = ObservableList<T>()
    list.addAll(items)
    return list
}