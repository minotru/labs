import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class IndexBase implements Serializable, Iterable<Integer> {
    private Map<String, Index> indexes;
    private ArrayList<Long> recordsPointers;
    private List<Integer> order;

    public IndexBase() {
        indexes = new HashMap<String, Index>();
        recordsPointers = new ArrayList<>();
        order = new ArrayList<>();
    }

    public Iterator<Integer> iterator() {
        return order.iterator();}

    public void orderBy(String indexName) {
        order = indexes.get(indexName).getAllIndexes();
    }


    public long getPointerFor(int row) {
        return recordsPointers.get(order.get(row));
    }

    public int get(int i) {
        return order.get(i);
    }

    public void reverseOrderBy(String indexName) {
        orderBy(indexName);
        Collections.reverse(order);
    }

    public List<Integer> findIf(String indexName, Predicate<Comparable> predicate) {
        List<Long> match = new ArrayList<>();
        return indexes.get(indexName)
                .findIndexesIf(predicate);
    }

    public boolean contains(String field, Comparable key) {
        return indexes.get(field).contains(key);
    }

    public void addIndex(String name, Index index) {
        indexes.put(name, index);
    }

    public void put(Object target, long memoryPointer) {
        recordsPointers.add(memoryPointer);
        int pointerInd = recordsPointers.size() - 1;
        for (Index index : indexes.values())
            index.put(target, pointerInd);
        order.add(pointerInd);
    }

    public void remove(int row) {
        int trueRow = order.get(row);
        for (Index index : indexes.values())
            index.remove(trueRow);
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
