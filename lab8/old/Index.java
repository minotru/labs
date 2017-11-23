import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;


interface KeyExtractor extends Serializable {
    Comparable extractKey(Object object);
}


public class Index implements Serializable {
    private KeyExtractor keyExtractor;
    private TreeMap<Comparable, List<Integer>> index;

    public Index(KeyExtractor keyExtractor) {
        this.keyExtractor = keyExtractor;
        this.index = new TreeMap<>();

    }

    public boolean contains(Comparable key) {
        return index.containsKey(key);
    }

    public void remove(int row) {
        for (Map.Entry<Comparable, List<Integer>> entry : index.entrySet())
            if (entry.getValue().contains(row)) {
                if (entry.getValue().size() == 1)
                    index.remove(entry.getKey());
                else
                    entry.getValue().remove(row);
                break;
            }
    }

    public List<Integer> findIndexes(Comparable key) {
        return index.get(key);
    }

    public void put(Object target, int row) {
        Comparable key = keyExtractor.extractKey(target);
        if (!contains(key))
            index.put(key, new ArrayList<Integer>());
        index.get(key).add(row);

    }

    public List<Integer> getAllIndexes() {
        List<Integer> indexes = new LinkedList<Integer>();
        index.forEach((key, value) -> indexes.addAll(value));
        return indexes;
    }

    public List<Integer> findIndexesIf(Predicate<Comparable> predicate) {
        List<Integer> indexes = new LinkedList<Integer>();
        index.forEach((key, value) -> {
            if (predicate.test(key))
                indexes.addAll(value);
        });
        return indexes;
    }

    public void clear() {
        index.clear();
    }
}