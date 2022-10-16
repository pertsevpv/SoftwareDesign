import java.util.*

class LRUCacheImpl<K, V>(capacity: Int) : LRUCache<K, V>(capacity) {

    private val keyToNode: MutableMap<K, Node> = mutableMapOf()

    override fun doSet(key: K, value: V) {
        removeNode(key)
        addNode(key, value)
    }

    override fun doGet(key: K): Optional<V> {
        if (!keyToNode.containsKey(key)) return Optional.empty()

        val res: V = keyToNode[key]!!.value

        doSet(key, res)
        return Optional.of(res)
    }

    override fun size(): Int = keyToNode.size

    private fun removeNode(key: K) {
        if (!keyToNode.containsKey(key)) return
        val current: Node = keyToNode[key] ?: return

        when (current.next) {
            null -> tail = current.prev
            else -> current.next!!.prev = current.prev
        }
        when (current.prev) {
            null -> head = current.next
            else -> current.prev!!.next = current.next
        }

        keyToNode.remove(key)
    }

    private fun addNode(key: K, value: V) {
        val newNode = Node(key, value)
        newNode.next = head

        if (head != null) head!!.prev = newNode
        head = newNode

        if (tail == null) tail = newNode
        keyToNode[key] = newNode

        if (size() > capacity) removeNode(tail!!.key)
    }

}