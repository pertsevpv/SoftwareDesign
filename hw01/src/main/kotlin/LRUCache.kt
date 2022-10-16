import java.util.*

abstract class LRUCache<K, V>(
    protected val capacity: Int
) {

    protected var head: Node? = null
    protected var tail: Node? = null

    init {
        assert(capacity > 0) { "Capacity should be positive" }
    }

    operator fun set(key: K, value: V) {
        assert(key != null) { "Key shouldn't be null" }
        assert(value != null) { "Value shouldn't be null" }

        doSet(key, value)

        assert(head != null) { "Value not inserted" }
        assert(tail != null) { "Value not inserted" }
        assert(head!!.value == value) { "Wrong value inserted" }
        assert(size() <= capacity) { "Too big cache size" }
    }

    operator fun get(key: K): Optional<V> {
        assert(key != null) { "Key shouldn't be null" }
        val oldSize = size()

        val result = doGet(key)

        assert(!result.isPresent || head!!.value == result.get()) { "Value not inserted" }
        assert(oldSize == size()) { "Size shouldn't be changed" }

        return result
    }

    open fun size(): Int {
        var cnt = 0
        var cur = head
        while (cur != null) {
            cur = cur.next
            cnt++
        }
        return cnt
    }

    abstract fun doSet(key: K, value: V)

    abstract fun doGet(key: K): Optional<V>

    protected inner class Node(val key: K, val value: V) {
        var prev: Node? = null
        var next: Node? = null

        init {
            assert(key != null) { "Key shouldn't be null" }
            assert(value != null) { "Value shouldn't be null" }
        }
    }
}