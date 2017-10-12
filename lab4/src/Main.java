import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static boolean isGood(RecordBook rb) {
        return (rb.sessions.stream().allMatch(session ->
            session.exams.stream().allMatch(exam -> exam.getMark() >= 9) &&
            session.credits.stream().allMatch(credit -> credit.hasPassed())));
    }

    public static void main(String[] args) {
        List<RecordBook> recordBooks = new ArrayList<RecordBook>();
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            int cnt = Integer.parseInt(reader.readLine());
            RecordBookReader rbReader = new RecordBookReader(reader);
            for (int i = 0; i < cnt; i++) {
                RecordBook rb = new RecordBook();
                rbReader.readRecordBook(rb);
                recordBooks.add(rb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try(Writer writer = new FileWriter("output.txt")) {
            System.out.println("All students:");
            for (RecordBook rb : recordBooks)
                System.out.println(rb);
            System.out.println("Good students:");
            for (RecordBook rb : recordBooks)
                if (isGood(rb)) {
                    System.out.println(rb.student.toString());
                    writer.write(rb.toString());
                }
            System.out.println("Check output.txt for more detailed information.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}