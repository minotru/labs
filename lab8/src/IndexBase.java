import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class IndexBase implements Serializable, Iterable<Long> {
    private Map<String, Index> indexes;
    private ArrayList<Long> recordsPointers;
    private List<Integer> order;

    public IndexBase() {
        indexes = new HashMap<String, Index>();
        recordsPointers = new ArrayList<>();
        order = new ArrayList<>();
    }

    public Iterator<Long> iterator() {
        return new Iterator<Long>() {
            private Iterator<Integer> it = order.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Long next() {
                return recordsPointers.get(it.next());
            }
        };
    }

    public void orderBy(String indexName) {
        order = indexes.get(indexName).getAllIndexes();
    }

    public Long get(int i) {
        return recordsPointers.get(order.get(i));
    }

    public void reverseOrderBy(String indexName) {
        orderBy(indexName);
        Collections.reverse(order);
    }

    public List<Long> findIf(String indexName, Predicate<Comparable> predicate) {
        List<Long> match = new ArrayList<>();
        indexes.get(indexName)
                .findIndexesIf(predicate)
                .forEach(ind -> match.add(recordsPointers.get(ind)));
        return match;
    }

    public boolean contains(String field, Comparable key) {
        return indexes.get(field).contains(key);
    }

    public void addIndex(String name, Index index) {
        indexes.put(name, index);
    }

    public void put(Object target, long value) {
        recordsPointers.add(value);
        int pointerInd = recordsPointers.size() - 1;
        for (Index index : indexes.values())
            index.put(target, pointerInd);
        order.add(pointerInd);
    }

    public void remove(int value) {
        for (Index index : indexes.values())
            index.remove(value);
    }

    public int size() {
        return recordsPointers.size();
    }

    public static IndexBase load(String fileName) throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois =
                new ObjectInputStream(
                        new FileInputStream(fileName))) {
            return (IndexBase) ois.readObject();
        }
    }

    public void save(String fileName) throws IOException {
        try (ObjectOutputStream ous =
                     new ObjectOutputStream(
                             new FileOutputStream(fileName))) {
            ous.writeObject(this);
            ous.flush();
        }
    }

}
