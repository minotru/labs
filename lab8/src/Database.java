import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class Database implements Serializable, Closeable {
    private String name;
    private IndexBase indexBase;
    private RandomAccessFile dataFile;

//    public Database(String name) throws IOException, ClassNotFoundException {
//        File dataBase = new File(name + ".db");
//        if (dataBase.exists())
//            open(name);
//        else
//            create(name);
//    }

//    private Database() {
//
//    }

    public static Database create(String name) {
        Database database = new Database();
        try {
            File file = new File(name + ".db");
            file.createNewFile();
            database.dataFile = new RandomAccessFile(file, "rw");
            database.name = name;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.indexBase = new IndexBase();
        return database;
    }

    public static Database open(String name) throws IOException, ClassNotFoundException {
        Database database = new Database();
        database.name = name;
        database.dataFile = new RandomAccessFile(name + ".db", "rw");
        database.indexBase = IndexBase.load(name + ".index");
        return database;
    }

    public void put(Serializable obj) throws IOException {
        long pos = Buffer.writeObject(dataFile, obj, false);
        indexBase.put(obj, pos);
    }

    public void addIndex(String forField, KeyExtractor keyExtractor) {
        indexBase.addIndex(forField, new Index(keyExtractor));
    }

    public Object get(int row) throws Exception{
        return Buffer.readObject(dataFile, indexBase.get(row));
    }

    public void sortBy(String fieldName) {
        indexBase.orderBy(fieldName);
    }

    public void reverseSortBy(String fieldName) {
        indexBase.reverseOrderBy(fieldName);
    }

    public List getAll() {
        List all = new ArrayList();
        try {
            for (Long ind : indexBase)
                all.add(Buffer.readObject(dataFile, ind));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return all;
    }

    public List getIf(String fieldName, Predicate<Comparable> predicate) {
        List match = new ArrayList();
        indexBase.findIf(fieldName, predicate)
                .forEach(pointer -> {
                    try {
                        match.add(Buffer.readObject(dataFile, pointer));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        return match;
    }


    public Iterator iterator() {
        return new Iterator() {
            private Iterator it = getAll().iterator();
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Object next() {
                return it.next();
            }
        };
    }

    @Override
    public void close() throws IOException {
        indexBase.save(name + ".index");
        if (dataFile != null)
            dataFile.close();
    }

    public boolean contains(String fieldName, Comparable key) {
        return indexBase.contains(fieldName, key);
    }

}
