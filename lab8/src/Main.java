import java.io.*;
import java.util.*;

public class Main {
    private static RandomAccessFile database;
    private  static  IndexBase indexBase;
    private static final String databaseName = "accounts";
    private static final String INVALID_ARGUMENT = "Argument is invalid";

    private static void printHelp() {
        System.out.println(
                "Syntax:\n" +
                        "\t-a  - append data\n" +
                        "\t-az - append data, compress every record\n" +
                        "\t-d  - clear all data\n" +
                        "\t-dk  {name|id|code} key - clear data by key\n" +
                        "\t-p  - print data unsorted\n" +
                        "\t-ps  {name|id|code} - print data sorted by name/id/code\n" +
                        "\t-psr {name|id|code} - print data reverse sorted by iname/id/code\n" +
                        "\t-f   {name|id|code} key - find record by key\n" +
                        "\t-fr  {name|id|code} key - find records > key\n" +
                        "\t-fl  {name|id|code} key - find records < key\n" +
                        "\t-h  - command line syntax\n"
        );
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }
        try (RandomAccessFile database1 = new RandomAccessFile(databaseName + ".db", "rw");
             IndexBase indexBase1 = IndexBase.load(databaseName)) {
            indexBase =indexBase1;
            database = database1;
            switch (args[0]) {
                case "-?":
                case "-h":
                    printHelp();
                    break;
                case "-a":
                    appendFile(false);
                    break;
                case "-az":
                    // Append file with compressed new object from System.in
                    // -az
                    appendFile(true);
                    break;
                case "-p": {
                    // Prints data file
                    List<Long> pointers = indexBase.ids.getAll();
                    Collections.sort(pointers);
                    printAccounts(readAccounts(pointers));
                    break;
                }
                case "-ps":
                    // Prints data file sorted by key
                    printAccounts(readAccounts(getIndex(args[1]).getAll()));
                    break;
                case "-psr": {
                    // Prints data file reverse-sorted by key
                    printAccountsReversed(readAccounts(getIndex(args[1]).getAll()));
                    break;
                }
                case "-d":
                    // delete files
                    if (args.length != 1) {
                        System.err.println("Invalid number of arguments");
                        System.exit(1);
                    }
                    deleteFile();
                    break;
                case "-dk":
                    // Delete records by key
                    deleteRecords(findPointersIf(args[1], args[2], 0));
                    break;

                case "-f":
                    // Find record(s) by key
                    printAccounts(readAccounts(findPointersIf(args[1], args[2], 0)));
                    break;
                case "-fr":
                    // Find record(s) by key large then key
                    printAccounts(readAccounts(findPointersIf(args[1], args[2], 1)));
                    break;
                case "-fl":
                    // Find record(s) by key less then key
                    printAccounts(readAccounts(findPointersIf(args[1], args[2], -1)));
                    break;
                default:
                    System.err.println("Option is not implemented: " + args[0]);
            }
        } catch (Exception e) {
            System.err.println("Run/time error: " + e);
            e.printStackTrace();
        }
    }

    // input file encoding:
    private static String encoding = "Cp866";

    private static BankAccount readAccount(long pointer) throws IOException, ClassNotFoundException {
        return (BankAccount) Buffer.readObject(database, pointer);
    }

    private static List<BankAccount> readAccounts(List<Long> pointers) throws
            IOException, ClassNotFoundException {
        List<BankAccount> accounts = new ArrayList<>();
        for (long pointer : pointers)
            accounts.add(readAccount(pointer));
        return accounts;
    }

    private static void printAccounts(List<BankAccount> accounts) {
        for (BankAccount account : accounts)
            System.out.println(account);
    }

    private static List<Long> findPointersIf(String fieldName, String strKey, int cmp) throws Exception{
        //getIndex(fieldName);
        if (fieldName.equals("name"))
            return indexBase.names.getIf(strKey, cmp);
        if (fieldName.equals("id"))
            return indexBase.ids.getIf(Long.parseLong(strKey), cmp);
        if (fieldName.equals("code"))
            return indexBase.codes.getIf(Integer.parseInt(strKey), cmp);
        else
            return null;
    }

    private static void printAccountsReversed(List<BankAccount> accounts) {
        Collections.reverse(accounts);
        printAccounts(accounts);
    }


    public static BankAccount readAccount() {
        String currencyCode;
        String name;
        long id;
        int code;
        double amount;
        double percent;
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Enter account id");
        do {
            id = sc.nextInt();
            if (indexBase.ids.contains(id))
                System.err.println("There is already account with such id");
        } while (indexBase.ids.contains(id));
        System.out.println("Enter owner name");
        sc.nextLine();
        name = sc.nextLine();
        System.out.println("Enter code");
        code = sc.nextInt();
        System.out.println("Enter currency");
        currencyCode = sc.next();
        System.out.println("Enter percent");
        percent = sc.nextDouble();
        System.out.println("Enter amount");
        amount = sc.nextDouble();
        BankAccount account  = new BankAccount(id, name, code, currencyCode, percent);
        account.setAmountOn(amount);
        return account;
    }


    private static void deleteBackup() {
        new File( databaseName + ".~db" ).delete();
        new File( databaseName + ".~index" ).delete();
    }

    static void deleteFile() {
        deleteBackup();
        new File(databaseName + ".db").delete();
        new File( databaseName + ".index").delete();
    }

    private static void backup() {
        deleteBackup();
        new File( databaseName + ".db" ).renameTo( new File( databaseName+".~db" ));
        new File( databaseName + ".index" ).renameTo( new File( databaseName + ".~index" ));
    }

    private static Index<?> getIndex(String key) throws Exception{
        if (key.equals("id"))
            return indexBase.ids;
        else if (key.equals("code"))
            return indexBase.codes;
        else if (key.equals("name"))
            return indexBase.names;
        else
            throw new Exception("index mismatch");
    }

    static void deleteRecords(List<Long> deletePointers) {
        backup();
        try (RandomAccessFile tmpDatabase = new RandomAccessFile(databaseName +  ".db", "rw");
            IndexBase tmpIndexBase = IndexBase.load(databaseName) ) {
            for (long pointer : indexBase.ids.getAll())
                if (!deletePointers.contains(pointer)) {
                    BankAccount acc = (BankAccount) Buffer.readObject(database, pointer);
                    long newPointer = Buffer.writeObject(tmpDatabase, acc, true);
                    tmpIndexBase.add(acc, newPointer);
                }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        deleteBackup();
        System.exit(1);
    }

    static void appendFile(Boolean zipped)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        BankAccount account = readAccount();
        if (account == null)
            return;
        long pointer = Buffer.writeObject(database, account, zipped);
        indexBase.add(account, pointer);
    }

}
