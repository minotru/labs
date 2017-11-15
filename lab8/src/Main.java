import java.io.IOException;
import java.util.*;

public class Main {
    private static Database database;
    private static final String BAD_ARGUMENTS = "Bad arguments list";

    public static List<BankAccount> createAccounts() {
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new BankAccount(
                1234567891230L,
                "Simon Karasik",
                1223,
                "USD",
                0.1
        ));
        accounts.add(new BankAccount(
           123,
           "Alexander Kovalchuk",
           1000,
           "RUB",
           0.2
        ));
        return accounts;
    }

    public  static void createDatabaseTest() {
        try (Database database = Database.create("accounts")) {
            database.addIndex("id",  o -> ((BankAccount)o).getId());
            database.addIndex("code", o -> ((BankAccount)o).getCode());
           // for (BankAccount account : createAccounts())
             //   database.put(account);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printList(List list) {
        for (Object obj : list)
            System.out.println(obj.toString());
    }


    public static void openDatabaseTest() {
        try  (Database database = Database.open("accounts")){
            database.sortBy("id");
            //printList(database.getAll());
            database.reverseSortBy("code");
            //printList(database.getAll());
            database.put(new BankAccount(
                    1999,
                    "Maria Kucheravenko",
                    1256,
                    "EUR",
                    0.2));
            printList(database.getAll());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void help() {
        System.out.println("" +
                "Syntax:\n"+
                "help - show help\n" +
                "append <?compress>- append record, <compress it>\n" +
                "clear - clear all records\n" +
                "clear <?fieldName: name|id|code> <key> -  clear records by key\n" +
                "print <?key: name|id|code> <?reverse>\n - print records\n" +
                "find <fieldName: name|id|code> <key> <?more/less> - find by key"
        );
    }

    public static BankAccount readAccount() {
        String currencyCode;
        String name;
        long id;
        int code;
        double amount;
        double percent;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter account id");
        id = sc.nextInt();
        if (database.contains("id", id)) {
            System.err.println("There is already account with such id");
            return null;
        }
        System.out.println("Enter owner name");
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

    public static void append(String[] args) throws IOException{
        if (args != null || (args.length > 0 && !args[0].equals("compressed")))
            System.err.println(BAD_ARGUMENTS);
        BankAccount account = readAccount();
        if (account != null)
            database.put(account);
    }

    public static void clear(String[] args) {
        if (args == null)
            database.clear();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Arguments list is empty");
            help();
            return;
        }
        try {
            String[] functionArgs = null;
            if(args.length > 1)
                functionArgs =  Arrays.copyOfRange(args, 1, args.length);
            database = Database.open("records");
            switch (args[0]) {
                case "help":
                    help();
                    break;
                case "append":
                    append(fuctionArgs);
                    break;
                case "clear":
                    clear(fuctionArgs);
                    break;
                case "print":
                    print(fuctionArgs);
                    break;
                case "find":
                    find(fuctionArgs);
                    break;
                default: {
                    System.err.println("Invalid command. Usage is:");
                    help();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
