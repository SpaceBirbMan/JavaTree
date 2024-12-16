import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Generic hash table with basic operations (put, find, remove, etc.)
 * @param <K> Key type
 * @param <V> Value type
 */
public class UltraHash<K, V> implements Iterable<K> {
    protected int numBuckets; // Number of buckets in the table
    protected LinkedList<Entry<K, V>>[] buckets; // Array of buckets (LinkedLists)
    protected int size; // Number of elements in the table

    // Constructor with specified number of buckets
    @SuppressWarnings("unchecked")
    public UltraHash(int numBuckets) {
        this.numBuckets = numBuckets;
        this.buckets = new LinkedList[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = new LinkedList<>(); // Initialize empty linked lists for each bucket
        }
        this.size = 0; // Initialize size to 0
    }

    // Default constructor with 16 buckets
    public UltraHash() {
        this(16); // Default number of buckets is 16
    }

    /**
     * Generates a hash value for a given key to map it to a valid bucket index.
     *
     * Time complexity: O(1) (Hashing operation)
     *
     * @param key The key to hash
     * @return The computed hash value (bucket index)
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % numBuckets; // O(1) - Compute the hash code and index
    }

    /**
     * Inserts a key-value pair into the hash table.
     * If the key already exists, it updates the value.
     *
     * Time complexity: O(n) in the worst case (if all elements are in the same bucket).
     * Average case: O(1) if keys are evenly distributed across buckets.
     *
     * @param key The key to insert
     * @param value The value associated with the key
     */
    public void put(K key, V value) {
        int bucketIndex = hash(key); // O(1) - Hashing the key
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        // Check if the key already exists in the bucket
        for (Entry<K, V> entry : bucket) { // O(n) - Traversing the bucket's list
            if (entry.key.equals(key)) {
                entry.value = value; // Update value if key exists
                return; // O(1) - Operation completed
            }
        }

        // If the key does not exist, add a new entry
        bucket.add(new Entry<>(key, value)); // O(1) - Add new entry to the list
        size++; // O(1) - Increase size of the table
    }

    /**
     * Finds a value associated with a given key in the hash table.
     *
     * Time complexity: O(n) in the worst case (if all elements are in the same bucket).
     * Average case: O(1) if keys are evenly distributed across buckets.
     *
     * @param key The key to find
     * @return The value associated with the key, or null if the key is not found
     */
    public V find(K key) {
        int bucketIndex = hash(key); // O(1) - Hashing the key
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        // Traverse the bucket's list to find the key
        for (Entry<K, V> entry : bucket) { // O(n) - Traversing the bucket's list
            if (entry.key.equals(key)) {
                return entry.value; // O(1) - Found the value
            }
        }
        return null; // O(1) - Key not found
    }

    /**
     * Removes the key-value pair from the hash table based on the given key.
     *
     * Time complexity: O(n) in the worst case (if all elements are in the same bucket).
     * Average case: O(1) if keys are evenly distributed across buckets.
     *
     * @param key The key to remove
     */
    public void remove(K key) {
        int bucketIndex = hash(key); // O(1) - Hashing the key
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex];

        // Traverse the bucket's list to find the key and remove the entry
        for (Entry<K, V> entry : bucket) { // O(n) - Traversing the bucket's list
            if (entry.key.equals(key)) {
                bucket.remove(entry); // O(n) - Remove the entry (LinkedList operation)
                size--; // O(1) - Decrease size of the table
                return;
            }
        }
    }

    /**
     * Clears all entries from the hash table.
     *
     * Time complexity: O(n) - Clear all elements in the table
     */
    public void clear() {
        for (int i = 0; i < numBuckets; i++) { // O(n) - Iterate over all buckets
            buckets[i] = new LinkedList<>(); // O(1) - Reinitialize each bucket
        }
        size = 0; // O(1) - Reset the size
    }

    /**
     * Returns the number of elements in the table.
     *
     * Time complexity: O(1) - Constant time operation
     *
     * @return The number of elements
     */
    public int size() {
        return size; // O(1) - Return the current size
    }

    /**
     * Returns an iterator for the keys in the hash table.
     *
     * Time complexity: O(1) - Creating the iterator
     *
     * @return An iterator for the keys
     */
    @Override
    public Iterator<K> iterator() {
        return new UltraHashIterator(); // O(1) - Create the iterator
    }

    /**
     * Iterator for the UltraHash table to iterate over the keys.
     */
    private class UltraHashIterator implements Iterator<K> {
        private int currentBucket = 0; // Current bucket being examined
        private Iterator<Entry<K, V>> currentIterator = buckets[0].iterator(); // Iterator for the current bucket

        /**
         * Checks if there are more elements in the iteration.
         *
         * Time complexity: O(n) - Traverse the buckets to find the next non-empty bucket
         *
         * @return true if there are more elements, false otherwise
         */
        @Override
        public boolean hasNext() {
            while (currentBucket < numBuckets) { // O(n) - Check each bucket
                if (currentIterator.hasNext()) {
                    return true; // O(1) - Found the next element
                }
                currentBucket++; // O(1) - Move to the next bucket
                if (currentBucket < numBuckets) {
                    currentIterator = buckets[currentBucket].iterator(); // O(1) - Get the iterator for the next bucket
                }
            }
            return false; // O(1) - No more elements
        }

        /**
         * Returns the next key in the iteration.
         *
         * Time complexity: O(1) - Constant time operation
         *
         * @return The next key
         * @throws NoSuchElementException if there are no more elements
         */
        @Override
        public K next() {
            if (!hasNext()) { // O(1) - Check if there are more elements
                throw new NoSuchElementException(); // O(1) - Throw exception if no elements left
            }
            return currentIterator.next().key; // O(1) - Return the next key
        }
    }

    /**
     * Key-value pair for storage in the hash table.
     *
     * @param <K> Key type
     * @param <V> Value type
     */
    protected static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
