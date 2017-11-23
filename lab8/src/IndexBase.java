import java.io.*;
import java.util.Comparator;

public class IndexBase implements AutoCloseable, Serializable {
    public final Index<String> names;
    public final Index<Long> ids;
    public final Index<Integer> codes;
    private String name;

    public static IndexBase load(String name) throws FileNotFoundException, IOException {
        IndexBase indexBase = null;
        if (!(new File(name + ".index")).exists())
            indexBase = new IndexBase(name);
        else
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(name + ".index"))) {
                indexBase = (IndexBase) ois.readObject();
                indexBase.name = name;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        return indexBase;
    }

    public void save() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(name + ".index"))) {
            oos.writeObject(this);
            oos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        save();
    }

    public IndexBase(String name) {
        names = new Index<String>(Comparator.naturalOrder());
        ids = new Index<Long>(Comparator.naturalOrder());
        codes = new Index<Integer>(Comparator.naturalOrder());
        this.name = name;
    }

    public void add(BankAccount account, long value) {
        names.put(account.getOwnerName(), value);
        ids.put(account.getId(), value);
        codes.put(account.getCode(), value);
    }

    public void remove(BankAccount account) {
        names.remove(account.getOwnerName());
        ids.remove(account.getId());
        codes.remove(account.getCode());
    }
}
