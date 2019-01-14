import java.util.LinkedHashMap;
import java.util.Map;

class SimpleLRUCacheUsingLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int cacheSize;
	private static final int DEFAULT_CACHE_CAPACITY = 16;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	SimpleLRUCacheUsingLinkedHashMap(int cacheSize) {
		this(DEFAULT_CACHE_CAPACITY, cacheSize);
	}

	SimpleLRUCacheUsingLinkedHashMap(int initialCapacity, int cacheSize) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR, cacheSize);
	}

	SimpleLRUCacheUsingLinkedHashMap(int initialCapacity, float loadFactor, int cacheSize) {
		super(initialCapacity, loadFactor, true);
		this.cacheSize = cacheSize;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
		return size() > cacheSize;
	}
}


public class Solution {
	public static void main(String... args) {
		Map<Integer, String> cache = new SimpleLRUCacheUsingLinkedHashMap<>(5);
		for (int i = 0; i < 10; i++) {
			cache.put(i, "hi");
		}
		// entries 0-4 have already been removed
		// entries 5-9 are ordered
		System.out.println("cache = " + cache);

		System.out.println(cache.get(7));
		// entry 7 has moved to the end
		System.out.println("cache = " + cache);

		for (int i = 10; i < 14; i++) {
			cache.put(i, "hi");
		}
		// entries 5,6,8,9 have been removed (eldest entries)
		// entry 7 is at the beginning now
		System.out.println("cache = " + cache);

		cache.put(42, "meaning of life");
		// entry 7 is gone too
		System.out.println("cache = " + cache);
	}
}
