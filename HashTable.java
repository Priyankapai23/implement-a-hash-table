class HashTable {
    private static final int INITIAL_SIZE = 8;
    private Entry[] table;
    private int size;
    private int numElements;

    // Constructor
    public HashTable() {
        table = new Entry[INITIAL_SIZE];
        size = INITIAL_SIZE;
        numElements = 0;
    }

    // Entry class holds key-value pairs
    private static class Entry {
        String key;
        String value;

        Entry(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    // Hash function:  sum of ASCII values of characters in the key
    private int hash(String key) {
        int hashValue = 0;
        for (int i = 0; i < key.length(); i++) {
            hashValue += key.charAt(i);
        }
        return hashValue % size;
    }

    // Load factor : Number of elements / size of table
    private double loadFactor() {
        return (double) numElements / size;
    }

    // Resize the hash table when the load factor exceeds 0.7
    private void resize() {
        size = size * 2; // Double the size
        Entry[] newTable = new Entry[size];

        // Rehashing all entries and placing them in the new table
        for (Entry entry : table) {
            if (entry != null) {
                int newIndex = hash(entry.key);
                newTable[newIndex] = entry;
            }
        }

        table = newTable; // Update table reference 
    }

    // Inserting a key-value pair
    public void put(String key, String value) {
        if (loadFactor() > 0.7) {
            resize();
        }

        int index = hash(key);

        // If no collision, place the key-value pair at the computed index
        if (table[index] == null) {
            table[index] = new Entry(key, value);
            numElements++;
        } else {
            // If there's a collision, overwrite the existing entry (simplified handling)
            table[index] = new Entry(key, value);
        }
    }

    // Retrieve a value by key
    public String get(String key) {
        int index = hash(key);
        if (table[index] != null && table[index].key.equals(key)) {
            return table[index].value;
        }
        return null; // Return null if key is not found
    }

    // Remove a key-value pair
    public String remove(String key) {
        int index = hash(key);
        if (table[index] != null && table[index].key.equals(key)) {
            String value = table[index].value;
            table[index] = null;
            numElements--;
            return value;
        }
        return null; // Return null if the key is not found
    }

    // Print the table (for debugging purposes)
    public void printTable() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.println("Index " + i + ": Key = " + table[i].key + ", Value = " + table[i].value);
            }
        }
    }

    public static void main(String[] args) {
        // Demonstration of the hash table functionality
        HashTable hashTable = new HashTable();

        // Insert some key-value pairs
        hashTable.put("apple", "fruit");
        hashTable.put("carrot", "vegetable");
        hashTable.put("dog", "animal");
        hashTable.put("elephant", "animal");

        // Retrieve values by key
        System.out.println(hashTable.get("apple"));  // Output: fruit
        System.out.println(hashTable.get("carrot")); // Output: vegetable
        System.out.println(hashTable.get("dog"));    // Output: animal
        System.out.println(hashTable.get("elephant")); // Output: animal

        // Remove a key-value pair
        System.out.println(hashTable.remove("dog"));  // Output: animal
        System.out.println(hashTable.get("dog"));     // Output: null

        // Resize testing: adding more elements to invoke resizing
        for (int i = 0; i < 20; i++) {
            hashTable.put("key" + i, "value" + i);
        }

        //To print the current state of the hash table
        hashTable.printTable();
    }
}