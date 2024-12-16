import java.util.*;

/**
 * UltraHash with automatic sorting - dictionary. Based on UltraHash
 * @param <K> Key
 * @param <V> Value
 */
public class UltraDict<K, V> extends UltraHash<K, V> {
    private final List<K> order; // Keeps track of insertion order of keys

    public UltraDict(int numBuckets) {
        super(numBuckets); // O(1) - Calls the parent constructor
        this.order = new ArrayList<>(); // O(1) - Initialize the order list
    }

    public UltraDict() {
        super(16); // O(1) - Default constructor with 16 buckets
        this.order = new ArrayList<>(); // O(1) - Initialize the order list
    }

    /**
     * Inserts a key-value pair into the dictionary.
     * If the key is new, it is added to the order list.
     * <p>
     * Time complexity: O(1) for insertion and key lookup, but the list insertion could take O(n) if the order list is large.
     *
     * @param key The key to insert
     * @param value The value associated with the key
     */
    public void put(K key, V value) {
        if (!containsKey(key)) { // O(1) - Check if the key already exists
            order.add(key); // O(1) - Add the key to the insertion order list
        }
        super.put(key, value); // O(1) - Insert the key-value pair in the hash table
    }

    /**
     * Retrieves the value associated with the given key.
     * <p>
     * Time complexity: O(n) in the worst case (if the key is in a bucket with many elements).
     * Average case: O(1) if the elements are evenly distributed across buckets.
     *
     * @param key The key whose associated value is to be retrieved
     * @return The value associated with the key, or null if not found
     */
    public V get(K key) {
        return find(key); // O(n) - Find the value in the hash table
    }

    /**
     * Checks if the dictionary contains the specified key.
     * <p>
     * Time complexity: O(n) in the worst case, O(1) in average case (if the key is well distributed).
     *
     * @param key The key to check
     * @return true if the dictionary contains the key, false otherwise
     */
    public boolean containsKey(K key) {
        return find(key) != null; // O(n) - Check if the key exists in the hash table
    }

    /**
     * Removes a key-value pair from the dictionary.
     * <p>
     * Time complexity: O(n) in the worst case (if the key is in a bucket with many elements).
     *
     * @param key The key to remove
     */
    public void remove(K key) {
        if (containsKey(key)) { // O(1) - Check if the key exists
            super.remove(key); // O(n) - Remove the key-value pair from the hash table
            order.remove(key); // O(n) - Remove the key from the insertion order list
        }
    }

    /**
     * Retrieves a list of all keys in the dictionary, in insertion order.
     * <p>
     * Time complexity: O(1) - Simply return the order list
     *
     * @return A list of keys in the dictionary
     */
    public List<K> keys() {
        return new ArrayList<>(order); // O(1) - Return the order list
    }

    /**
     * Retrieves a list of all values in the dictionary, in insertion order of the keys.
     * <p>
     * Time complexity: O(n) - Iterate over the order list and fetch values
     *
     * @return A list of values in insertion order
     */
    public List<V> values() {
        List<V> values = new ArrayList<>();
        for (K key : order) { // O(n) - Iterate over the keys
            values.add(get(key)); // O(1) - Fetch the value for each key
        }
        return values;
    }

    /**
     * Retrieves a list of all key-value pairs in the dictionary, in insertion order.
     * <p>
     * Time complexity: O(n) - Iterate over the keys and create entries
     *
     * @return A list of key-value pairs in insertion order
     */
    public List<Map.Entry<K, V>> entries() {
        List<Map.Entry<K, V>> entries = new ArrayList<>();
        for (K key : order) { // O(n) - Iterate over the keys
            entries.add(new AbstractMap.SimpleEntry<>(key, get(key))); // O(1) - Create an entry for each key-value pair
        }
        return entries;
    }

    /**
     * Returns the number of elements in the dictionary.
     * <p>
     * Time complexity: O(1) - Return the size
     *
     * @return The number of key-value pairs
     */
    public int size() {
        return super.size(); // O(1) - Return the size from the superclass
    }

    /**
     * Clears the dictionary by removing all key-value pairs and resetting the order list.
     * <p>
     * Time complexity: O(n) - Clears the hash table and the order list
     */
    public void clear() {
        super.clear(); // O(n) - Clear the hash table
        order.clear(); // O(n) - Clear the order list
    }

    /**
     * Merges another dictionary into this one, adding all key-value pairs.
     * <p>
     * Time complexity: O(n + m) where n is the size of the current dictionary and m is the size of the other dictionary.
     *
     * @param other The other dictionary to merge
     */
    public void merge(UltraDict<K, V> other) {
        for (Map.Entry<K, V> entry : other.entries()) { // O(m) - Iterate over the other dictionary's entries
            this.put(entry.getKey(), entry.getValue()); // O(1) - Add the entry to this dictionary
        }
    }
}
