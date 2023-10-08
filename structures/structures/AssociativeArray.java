package structures;

import java.util.Arrays;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Audrey Trinh
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
    // +-----------+---------------------------------------------------
    // | Constants |
    // +-----------+

    /**
     * The default capacity of the initial array.
     */
    static final int DEFAULT_CAPACITY = 16;

    // +--------+------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The size of the associative array (the number of key/value pairs).
     */
    int size;

    /**
     * The array of key/value pairs.
     */
    KVPair<K, V>[] pairs;

    // +--------------+------------------------------------------------
    // | Constructors |
    // +--------------+

    /**
     * Create a new, empty associative array.
     */
    @SuppressWarnings({ "unchecked" })
    public AssociativeArray() {
        // Creating new arrays is sometimes a PITN.
        this.pairs = (KVPair<K, V>[]) newInstance(KVPair.class, DEFAULT_CAPACITY);
        this.size = 0;
    } // structures.AssociativeArray()

    // +------------------+--------------------------------------------
    // | Standard Methods |
    // +------------------+

    /**
     * Create a copy of this structures.AssociativeArray.
     */
    public AssociativeArray<K, V> clone() {
        AssociativeArray<K, V> cloneArr = new AssociativeArray<>();
        cloneArr.size = this.size;
        // if the size of pairs associative array is larger than the length of underlying array
        if (cloneArr.size > cloneArr.pairs.length) {
            // expand the underlying array
            cloneArr.expand();
        }
        for (int i = 0; i < cloneArr.size; i++) {
            // copy the array into the clone
            cloneArr.pairs[i] = this.pairs[i].clone();
        }

        return cloneArr;
    } // clone()

    /**
     * Convert the array to a string.
     */
    public String toString() {
        StringBuilder printArr = new StringBuilder("");
        if (this.size == 1) {
            return "{" + this.pairs[0].toString() + " }";
        }
        for (int i = 0; i < this.size - 1; i++) {
            printArr.append(this.pairs[i].toString() + ",");
        }
        if (this.size > 0) {
        printArr.append(this.pairs[this.size - 1]);
        }
        return "{" + printArr + " }"; // STUB
    } // toString()

    // +----------------+----------------------------------------------
    // | Public Methods |
    // +----------------+

    /**
     * Set the value associated with key to value. Future calls to
     * get(key) will return value.
     */
    public void set(K key, V value) throws Exception {
        // if the size of pairs associative array is equal to the length of underlying array
        if (this.size == this.pairs.length) {
            // expand for set
            expand();
        }
        if (key == null || value == null) {
            // not allow null key and null value
            throw new Exception("Key and value cannot be null");
        }
        for (int i = 0; i < this.size; i++) {
            if (this.pairs[i].key.equals(key)) {
                // update the value if the key already exists
                this.pairs[i].value = value;
                return;
            }
        }
        this.pairs[this.size] = new KVPair<>(key, value);   
        this.size++;
    }

    /**
     * Get the value associated with key.
     *
     * @throws KeyNotFoundException
     *                              when the key does not appear in the associative
     *                              array.
     */
    public V get(K key) throws KeyNotFoundException {
        for (int i = 0; i < this.size; i++) {
            if (this.pairs[i].key.equals(key)) {
                return this.pairs[i].value;
            }
        }
        throw new KeyNotFoundException("There is no key in the array");
    } // get(K)

    /**
     * Determine if key appears in the associative array.
     */
    public boolean hasKey(K key) {
        for (int i = 0; i < this.size; i++) {
            if (this.pairs[i].key.equals(key)) {
                return true;
            }
        }
        return false;
    } // hasKey(K)

    /**
     * Remove the key/value pair associated with a key. Future calls
     * to get(key) will throw an exception. If the key does not appear
     * in the associative array, does nothing.
     */
    public void remove(K key) throws Exception {
        if (this.size == 0) {
            throw new Exception ("Cannot remove empty array");
        }
        for (int i = 0; i < this.size; i++) {
            if (this.pairs[i].key.equals(key)) {
                // replace the removed field with last field
                this.pairs[i].key = this.pairs[this.size - 1].key;
                this.pairs[i].value = this.pairs[this.size - 1].value;
                this.pairs[this.size - 1] = null;
                this.size--;
            }
        }
    } // remove(K)

    /**
     * Determine how many values are in the associative array.
     */
    public int size() {
        return this.size;
    } // size()

    // +-----------------+---------------------------------------------
    // | Private Methods |
    // +-----------------+

    /**
     * Expand the underlying array.
     */
    public void expand() {
        this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
    } // expand()

    /**
     * Find the index of the first entry in `pairs` that contains key.
     * If no such entry is found, throws an exception.
     */
    public int find(K key) throws KeyNotFoundException {
        for (int i = 0; i < this.size; i++) {
            if (this.pairs[i].key.equals(key)) {
                return i;
            }
        }
        throw new KeyNotFoundException("The key " + key + " does not exist in the array");
    } // find(K)

} // class structures.AssociativeArray