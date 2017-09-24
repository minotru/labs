/*Simon Karasik, lab 3, task 1.
Task: 
В каждом слове текста k-ю букву заменить заданными симво­лами. 
Если k больше длины слова, корректировку не выполнять.*/

import java.util.Scanner;
import java.util.StringTokenizer;

public class Lab3_1 {
    static String replacePattern(String s, String pattern, int at) {
        final String delims = ".,!:;\"\''\n\t\r\f ";
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(s, delims, true);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (delims.indexOf(token) != -1 || token.length() < at)
                sb.append(token);
            else {
                if (at > 0)
                    sb.append(token.subSequence(0, at));
                sb.append(pattern);
                if (at + 1 < token.length())
                    sb.append(token.subSequence(at + 1, token.length()));
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        String[] text = new String[100];
        int k, cnt = 0;
        String pattern;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter text. Finish input with EOF(Ctrl+Z).");
        while (sc.hasNextLine()) 
            text[cnt++] = new String(sc.nextLine());
        sc = new Scanner(System.in);
        System.out.println("Enter k - index of letter in a word to replace.");
        k = sc.nextInt();
        System.out.println("Enter pattern for replacment.");    
        pattern = sc.next();
        
        for (int i= 0; i < cnt; i++)
            text[i] = replacePattern(text[i], pattern, k);
        
        System.out.println("After replacment:");
        for (int i = 0 ; i < cnt; i++)
            System.out.println(text[i]);
    }
}