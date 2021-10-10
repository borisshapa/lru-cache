package lrucache

abstract class AbstractLRUCache<K, V>(val maxSize: Int) {
    protected val cacheMap = HashMap<K, Node>()
    protected var tail: Node? = null
    protected var head: Node? = null

    fun size(): Int {
        val result = doSize()
        assert(result in 0..maxSize)
        return result
    }
    
    fun put(key: K, value: V) {
        val oldSize = size()
        doPut(key, value)
        assert(size() == oldSize + 1 || (size() == oldSize && oldSize == maxSize))
        assert(head != null || maxSize == 0)
        assert(tail != null || maxSize == 0)
        assert(tail == null || tail?.key == key)
        assert(tail == null || tail?.value == value)
    }
    
    fun get(key: K): V? {
        val oldSize = size()
        val result = doGet(key)
        assert(size() == oldSize)
        assert(tail == null || (tail!!.key == key && tail!!.value == result) || result == null)
        return result
    }

    fun remove(key: K) {
        val oldSize = size()
        doRemove(key)
        assert(oldSize - size() in 0..1)
    }

    fun clear() {
        doClear()
        assert(size() == 0)
        assert(head == null)
        assert(tail == null)
    }

    protected abstract fun doSize(): Int
    protected abstract fun doPut(key: K, value: V)
    protected abstract fun doGet(key: K): V?
    protected abstract fun doRemove(key: K)
    protected abstract fun doClear()

    protected inner class Node(val key: K, val value: V) {
        var prev: Node? = null
        var next: Node? = null
    }
}