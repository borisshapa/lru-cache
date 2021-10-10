package lrucache

class LRUCache<K, V>(maxSize: Int) : AbstractLRUCache<K, V>(maxSize) {
    override fun doSize(): Int {
        return cacheMap.size
    }

    override fun doPut(key: K, value: V) {
        if (cacheMap.size >= maxSize) {
            if (head == null) {
                assert(maxSize == 0)
                return
            }
            doRemove(head!!.key)
        }
        updateNode(key, value)
    }

    override fun doGet(key: K): V? {
        val value = cacheMap[key]?.value ?: return null
        updateNode(key, value)
        return value
    }

    private fun updateNode(key: K, value: V) {
        doRemove(key)

        val newNode = Node(key, value)
        if (head == null) {
            head = newNode
        }

        tail?.next = newNode
        newNode.prev = tail
        tail = newNode
        cacheMap[key] = newNode
    }
    
    override fun doRemove(key: K) {
        val node = cacheMap[key] ?: return
        val prev = node.prev
        val next = node.next
        if (prev == null) {
            head = next
        } else {
            node.prev!!.next = next
        }
        
        if (next == null) {
            tail = prev
        } else {
            node.next!!.prev = prev
        }
        cacheMap.remove(key)
    }

    override fun doClear() {
        cacheMap.clear()
        head = null
        tail = null
    }
}