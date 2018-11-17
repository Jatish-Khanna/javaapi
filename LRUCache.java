import java.util.HashMap;

public class Solution {

    // Driver program to test the implementation of LRU
    public static void main(String[] args) {
        LRUCache lrucache = new LRUCache();
        // Add entries to cache
        lrucache.putEntry(1, 1);
        lrucache.putEntry(10, 15);
        lrucache.putEntry(15, 10);
        lrucache.putEntry(10, 16);
        lrucache.putEntry(12, 15);
        lrucache.putEntry(18, 10);
        lrucache.putEntry(13, 16);

        // Visit and update the cache with entry as recently used i.e. push it to first
        System.out.println(lrucache.getEntryValue(18));
        System.out.println(lrucache.getEntryValue(1));
        System.out.println(lrucache.getEntryValue(10));
        System.out.println(lrucache.getEntryValue(15));
        System.out.println(lrucache.getEntryValue(16));
        System.out.println(lrucache.getEntryValue(10));
    }
}

class LRUCache<K,V> {



    /**
     * Add the entry to Cache
     * @param key the item entry will be identified with
     * @param data value of the entry
     */
    public void putEntry(K key, V data) {
        // Check if entry is already present in the cache
        if (cacheMap.containsKey(key)) {
            // Get the found entry
            Entry<K,V> found = getEntry(key);
            // Update the entry with data
            found.data = data;
        } else {
            // Create new entry to be stored in cache
            Entry<K, V> newEntry = new Entry<>(key, data);
            // check if cache is FULL
            if (cacheMap.size() >= LRU_CACHE_SIZE) {
                // Remove LRU (Least recently used) entry from the dictionary
                cacheMap.remove(front.key);
                // remove entry from the cache memory
                removeEntry(front);
            }
            // Add the new entry to the dictionary identified with the Id as key
            cacheMap.put(key, newEntry);
            // add the new entry to the cache
            addFirstEntry(newEntry);
        }
    }

    /**
     * Get entry value from the cache
     * @param key the identification with which entry is store in cache
     * @return value respective to entry stored in the cache
     */
    public V getEntryValue(K key) {
        // Get the entry stored in the cache
        Entry<K,V> foundEntry = getEntry(key);
        // If entry not found return null, else data
        return foundEntry == null ? null : foundEntry.data;
    }

    /**
     * Fetch entry stored in the cache
     * @param key identification with which entry is stored in map
     * @return Entry stored in cache or null
     */
    private Entry<K,V> getEntry(K key) {
        // If entry is found in dictionary; return the stored entry
        if (cacheMap.containsKey(key)) {
            // Get the entry stored in dictionary
            Entry<K, V> foundEntry = cacheMap.get(key);
            // remove entry from the cache
            removeEntry(foundEntry);
            // Update the entry to be first visited entry
            addFirstEntry(foundEntry);
            // return the entry
            return foundEntry;
        }
        // return null if not found
        return null;
    }

    /**
     * Add the entry in the doubly linked list
     * @param kvEntry entry to be stored in cache
     */
    private void addFirstEntry(Entry<K, V> kvEntry) {
        // Check if No entry exists in the cache; add the first entry
        if (rear == null) {
            rear = kvEntry;
            front = kvEntry;
        } else {
            // Doubly linked is not Empty
            // new entries right is rear
            kvEntry.right = rear;
            // new entries left is null; when entry found during search (it may have left)
            kvEntry.left = null;
            // Update the left of last rear as new entry
            rear.left = kvEntry;
            // new rear is entry
            rear = kvEntry;
        }

    }

    /**
     * Remove entry from the cache
     * @param kvEntry entry to be removed from the cache
     */
    private void removeEntry(Entry<K, V> kvEntry) {
        // Update doubly linked list pointers
        // if left is not null - update left's right to entries right
        if (kvEntry.left != null) {
            kvEntry.left.right = kvEntry.right;
        }
        // entries is first in the list - next would be rear
        else {
            rear = kvEntry.right;
        }

        // if right is not null - update right's left as entries left
        if (kvEntry.right != null) {
            kvEntry.right.left = kvEntry.left;
        }
        // entry is last in the list - front would be left of entry
        else {
            front = kvEntry.left;
        }
    }

    /**
     * Data structure to store each entry as doubly linked list node
     * @param <K> key to identify entry
     * @param <V> value stored w.r.t. to the key
     */
}
