import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class SimpleHashMap<K, V> {

	static class Entry<K, V> {
		int eHash;
		K key;
		V value;
		Entry<K, V> next;

		Entry(int eHash, K key, V value) {
			super();
			this.eHash = eHash;
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "Entry [key=" + key + ", value=" + value + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(key) ^ Objects.hashCode(value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object check) {
			if (check != null && check.getClass() != this.getClass())
				return false;
			if (this == check)
				return true;
			Entry<K, V> wrappedCheck = (Entry<K, V>) check;
			return (Objects.equals(this.getKey(), wrappedCheck.getKey())
					&& Objects.equals(this.getValue(), wrappedCheck.getValue()));
		}

	}

	private enum Operations {
		REMOVE, INSERT
	};

	private Entry<K, V>[] table;

	private int tableSize;

	private int numberOfEntries;

	static final int DEFAULT_TABLE_CAPACITY = 16;

	static final int HUGE_TABLE_CAPACITY = 1 << 30;

	public SimpleHashMap() {
		super();

	}

	public SimpleHashMap(int initialSize) throws IllegalAccessException {
		super();
		if (initialSize < 0)
			throw new IllegalAccessException("Invalid table size specified");
		createTable(initialSize);
	}

	@SuppressWarnings("unchecked")
	private Entry<K, V>[] createTable(int capacity) {
		Entry<K, V>[] newTable = null;

		if (table == null || tableSize < 1) {
			tableSize = DEFAULT_TABLE_CAPACITY;
		} else if (capacity > 0) {
			int msbPosition = findMSB(capacity);
			tableSize = msbPosition;
		}
		newTable = new Entry[tableSize];
		numberOfEntries = 0;

		return newTable;
	}

	private int findMSB(int capacity) {
		int calculate = (int) (Math.log(capacity) / Math.log(2));
		return (int) (Math.pow(2, calculate));
	}

	public Entry<K, V>[] entrySet() {
		return table;
	}

	public int size() {
		return tableSize;
	}

	public boolean isEmpty() {
		return tableSize == 0;
	}

	final int hash(Object key) {
		int calculatedHash = Objects.hashCode(key);
		return (calculatedHash) ^ (calculatedHash >>> 16);
	}

	private boolean compareKeys(Entry<K, V> entry, Object key) {
		if (entry.getKey() == key || (key != null && key.equals(entry.getKey())))
			return true;
		return false;
	}

	private Entry<K, V> createEntry(int hash, K key, V value) {
		numberOfEntries++;
		return new Entry<>(hash, key, value);
	}

	public V put(K key, V value) {
		if (table == null || tableSize < 1)
			table = createTable(0);
		return addEntry(hash(key), key, value);
	}

	private V addEntry(int hash, K key, V value) {

		if (table == null || tableSize < 1)
			table = createTable(0);

		resize(Operations.INSERT);

		return addEntryByHash(hash, key, value, table, tableSize);
	}

	private V addEntryByHash(int hash, K key, V value, Entry<K, V>[] map, int tableLenght) {
		Entry<K, V> entry = getEntryByHash(hash, key);
		V currentValue = null;
		if (entry != null) {
			if (compareKeys(entry, key)) {
				currentValue = entry.getValue();
				entry.value = value;
			} else {
				entry.next = createEntry(hash, key, value);
			}
		} else {
			map[(tableLenght - 1) & hash] = createEntry(hash, key, value);
		}

		return currentValue;

	}

	private void addEntryByHash(int hashCode, Entry<K, V> sourceEntry) {
		addEntryByHash(sourceEntry.eHash, sourceEntry.getKey(), sourceEntry.getValue(), table, tableSize);
	}

	public V remove(K key) {

		if (table == null || tableSize < 1)
			return null;

		return removeEntry(hash(key), key);
	}

	private V removeEntry(int hash, K key) {
		Entry<K, V> entry = removeEntryByHash(hash, key);
		if (entry != null) {
			numberOfEntries--;
			resize(Operations.REMOVE);
			return entry.value;
		} else {
			return null;
		}
	}

	private Entry<K, V> removeEntryByHash(int hash, K key) {

		int index = (tableSize - 1) & hash;
		Entry<K, V> previousEntry = null;
		Entry<K, V>[] map = table;

		Entry<K, V> entry = map[index];

		if (entry == null)
			return null;

		while (entry != null) {
			if (compareKeys(entry, key)) {
				if (previousEntry == null) {
					map[index] = entry.next;
				} else {
					previousEntry.next = entry.next;
				}
				return entry;
			}
			previousEntry = entry;
			entry = entry.next;
		}
		return null;
	}

	private void resize(Operations operation) {

		Entry<K, V>[] currentMap = table;
		int newSize = tableSize;

		if (operation.equals(Operations.INSERT) && (tableSize << 3) <= numberOfEntries) {
			newSize = Math.min(HUGE_TABLE_CAPACITY, tableSize << 1);
		} else if (operation.equals(Operations.REMOVE) && (tableSize << 1) >= numberOfEntries) {
			newSize = Math.max(DEFAULT_TABLE_CAPACITY, tableSize >> 1);
		}

		if (newSize == tableSize)
			return;
		table = createTable(newSize);

		for (Entry<K, V> sourceEntry : currentMap) {
			if (sourceEntry == null) {
				continue;
			} else {
				do {
					addEntryByHash(sourceEntry.eHash, sourceEntry);
					// map[(newSize - 1) & sourceEntry.hashCode] = sourceEntry;
					sourceEntry = sourceEntry.next;
				} while (sourceEntry != null);
			}
		}

	}

	public V get(K key) {
		Entry<K, V> entry;
		return ((entry = getEntry(key)) != null ? entry.getValue() : null);
	}

	private Entry<K, V> getEntry(Object key) {
		Entry<K, V> entry;
		return ((entry = getEntryByHash(hash(key), key)) != null ? entry : null);
	}

	public boolean containsKey(Object key) {
		return getEntryByHash(hash(key), key) != null;
	}

	private Entry<K, V> getEntryByHash(int hash, Object key) {
		Entry<K, V> entry;
		Entry<K, V>[] map = table;

		if (map == null || tableSize < 1)
			return null;
		entry = map[(tableSize - 1) & hash];
		if (entry == null)
			return null;

		while (entry.next != null && entry.eHash != hash) {
			if (compareKeys(entry, key)) {
				return entry;
			}
			entry = entry.next;
		}
		return entry;
	}

}

class Solution {
	public static void main(String[] args) {
		// HashMap<Integer, Integer> k = new HashMap<>();
		SimpleHashMap<Integer, Integer> in = new SimpleHashMap<>();
		// System.out.println(in.put(1, 1));
		//
		// System.out.println(in.get(2));
		// System.out.println(in.get(null));
		// System.out.println(in.put(1, 2));
		// System.out.println(in.get(1));
		// System.out.println(in.put(1, 3));
		// System.out.println(in.get(1));
		//
		// System.out.println(in.put(1, 2));
		// System.out.println(in.put(2, 2));
		// System.out.println(in.put(3, 2));
		// System.out.println(in.put(4, 2));
		// System.out.println(in.put(5, 2));
		// System.out.println(in.put(6, 2));
		// System.out.println(in.put(7, 2));
		// System.out.println(in.put(8, 2));
		// System.out.println(in.put(9, 2));
		// System.out.println(in.put(10, 2));
		// System.out.println(in.put(11, 2));
		// System.out.println(in.put(16, 2));
		// System.out.println(in.put(17, 2));
		// System.out.println(in.put(null, 10));
		//
		// System.out.println(in.get(10));
		// System.out.println(in.get(8));
		// System.out.println(in.get(9));
		// System.out.println(in.get(7));
		// System.out.println(in.get(6));
		// System.out.println(in.get(null));
		// System.out.println(in.remove(17));
		// System.out.println(in.get(null));

		for (int i = 0; i < 256; i++) {
			in.put(i, i);
		}

		Map<Integer, Integer> m = new HashMap<>();

		for (int i = 0; i < 256; i++) {

		}
		m.entrySet().stream().forEach(element -> System.out.println(element));

		for (int i = 0; i < 256; i++) {
			System.out.println(in.remove(i));
		}
	}
}
