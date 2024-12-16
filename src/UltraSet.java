import java.util.List;
import java.util.ArrayList;

/**
 * Set, based on UltraHash with basic operations
 * @param <E>
 */
public class UltraSet<E> extends UltraHash<E, Boolean> {

    // Boolean used 'cause what else may be used as value if it is not necessary?

    public UltraSet(int numBuckets) {
        super(numBuckets); // O(1) - Calls the parent constructor
    }

    public UltraSet() {
        super(16); // O(1) - Default constructor with 16 buckets
    }

    /**
     * Adds an element to the set.
     * <p>
     * Time complexity: O(1) on average, O(n) in the worst case (when all elements hash to the same bucket).
     *
     * @param element The element to add
     */
    public void add(E element) {
        put(element, true); // O(1) - Insert element with value 'true' (using the put method of UltraHash)
    }

    /**
     * Checks if the set contains the specified element.
     * <p>
     * Time complexity: O(n) in the worst case (if elements are colliding in the same bucket).
     * Average case: O(1) if the elements are evenly distributed across buckets.
     *
     * @param element The element to check
     * @return true if the element is in the set, false otherwise
     */
    public boolean contains(E element) {
        return find(element) != null; // O(n) - Check if the element exists
    }

    /**
     * Removes the specified element from the set.
     * <p>
     * Time complexity: O(n) in the worst case (if elements are colliding in the same bucket).
     * Average case: O(1) if the elements are evenly distributed across buckets.
     *
     * @param element The element to remove
     */
    public void remove(E element) {
        super.remove(element); // O(n) - Remove the element (uses the remove method of UltraHash)
    }

    /**
     * Returns the number of elements in the set.
     * <p>
     * Time complexity: O(1) - Constant time to return the size
     *
     * @return The number of elements in the set
     */
    public int size() {
        return super.size(); // O(1) - Size is maintained in the superclass
    }

    /**
     * Clears the set by removing all elements.
     * <p>
     * Time complexity: O(n) - Clears all elements
     */
    public void clear() {
        super.clear(); // O(n) - Clear all elements using the clear method of UltraHash
    }

    /**
     * Converts the set into a List of elements.
     * <p>
     * Time complexity: O(n) - Iterates through all buckets and their elements
     *
     * @return A List of elements in the set
     */
    public List<E> toList() {
        List<E> elements = new ArrayList<>();
        for (int i = 0; i < numBuckets; i++) { // O(n) - Iterate through buckets
            for (UltraHash.Entry<E, Boolean> entry : buckets[i]) { // O(m) - Iterate through the entries in each bucket
                elements.add(entry.key); // O(1) - Add the key to the list
            }
        }
        return elements;
    }

    /**
     * Returns the union of the current set and another set.
     * <p>
     * Time complexity: O(n + m) where n is the size of the current set and m is the size of the other set.
     *
     * @param other The other set to union with
     * @return A new UltraSet containing the union of both sets
     */
    public UltraSet<E> union(UltraSet<E> other) {
        UltraSet<E> result = new UltraSet<>(numBuckets);
        for (E element : this.toList()) { // O(n) - Iterate through the current set
            result.add(element); // O(1) - Add element to the result set
        }
        for (E element : other.toList()) { // O(m) - Iterate through the other set
            result.add(element); // O(1) - Add element to the result set
        }
        return result;
    }

    /**
     * Returns the intersection of the current set and another set.
     * <p>
     * Time complexity: O(n * m) in the worst case, where n is the size of the current set and m is the size of the other set.
     *
     * @param other The other set to intersect with
     * @return A new UltraSet containing the intersection of both sets
     */
    public UltraSet<E> intersection(UltraSet<E> other) {
        UltraSet<E> result = new UltraSet<>(numBuckets);
        for (E element : this.toList()) { // O(n) - Iterate through the current set
            if (other.contains(element)) { // O(1) - Check if the other set contains the element
                result.add(element); // O(1) - Add the element to the result set
            }
        }
        return result;
    }

    /**
     * Returns the difference between the current set and another set.
     * <p>
     * Time complexity: O(n * m) in the worst case, where n is the size of the current set and m is the size of the other set.
     *
     * @param other The other set to subtract
     * @return A new UltraSet containing the difference
     */
    public UltraSet<E> difference(UltraSet<E> other) {
        UltraSet<E> result = new UltraSet<>(numBuckets);
        for (E element : this.toList()) { // O(n) - Iterate through the current set
            if (!other.contains(element)) { // O(1) - Check if the other set does not contain the element
                result.add(element); // O(1) - Add the element to the result set
            }
        }
        return result;
    }
}
