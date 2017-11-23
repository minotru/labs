import java.io.Serializable;
import java.util.*;

public class Index<T> implements Serializable{
    private TreeMap<T, List<Long>> map;

    public Index() {
        map = new TreeMap<>();
    }

    public Index(Comparator<T> comparator) {
        map = new TreeMap<T, List<Long>>(comparator);
    }

    public void put(T key, long value) {
        if (!contains(key))
            map.put(key, new ArrayList<Long>());
        map.get(key).add(value);
    }

    public boolean contains(T key) {
        return map.containsKey(key);
    }

    public List<Long> getAll() {
        List<Long> all = new ArrayList<>();
        for (List<Long> list : map.values())
            all.addAll(list);
        return all;
    }

    public void clear() {
        map.clear();
    }

    public void remove(T key) {
        map.remove(key);
    }

    public List<Long> get(T key) {
        return map.get(key);
    }

    public List<Long> getIf(T key, int compareResult) {
        if (compareResult == 0)
            get(key);
        Comparator<? super T> comparator = map.comparator();
        List<Long> match = new ArrayList<>();
        for (Map.Entry<T, List<Long>> entry : map.entrySet())
            if (comparator.compare(entry.getKey(), key) * compareResult > 0 ||
                    comparator.compare(entry.getKey(), key) == compareResult)
                match.addAll(entry.getValue());
        return match;
    }

    public List<Long> getLess(T key) {
       return getIf(key, -1);
    }

    public List<Long> getMore(T key) {
        return getIf(key, 1);
    }
}

class UniqueIndex<T> extends Index<T> {
    @Override
    public void put(T key, long value) {
        if (contains(key))
            remove(key);
        super.put(key, value);
    }

    public UniqueIndex(Comparator<T> comparator) {
        super(comparator);
    }

    public UniqueIndex() {
        super();
    }
}
