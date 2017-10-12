import java.io.Reader;
import java.util.Scanner;

public class RecordBookReader {
    private Scanner sc;

    public RecordBookReader(Reader reader) {
        this.sc = new Scanner(reader);
    }

    public void readRecordBook(RecordBook rb) throws Exception {

        readStudent(rb.student);
        int sessionCnt = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < sessionCnt; i++) {
            int number = sc.nextInt();
            sc.nextLine();
            RecordBook.Session session = new RecordBook.Session(number);
            readSession(session);
            rb.sessions.add(session);
        }
    }

    public void readSession(RecordBook.Session session) throws Exception{
        int examsCnt = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < examsCnt; i++) {
            RecordBook.Session.Exam exam = new RecordBook.Session.Exam();
            readExam(exam);
            session.exams.add(exam);
        }
        int creditsCnt = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < creditsCnt; i++) {
            RecordBook.Session.Credit credit = new RecordBook.Session.Credit();
            readCredit(credit);
            session.credits.add(credit);
        }
    }

    public void readExam(RecordBook.Session.Exam exam) throws Exception {
        exam.setSubject(sc.nextLine());
        exam.setTeacher(sc.nextLine());
        exam.setMark(Integer.parseInt(sc.nextLine()));
    }

    public void readCredit(RecordBook.Session.Credit credit) {
        credit.setSubject(sc.nextLine());
        credit.setTeacher(sc.nextLine());
        credit.setPassed(Boolean.parseBoolean(sc.nextLine()));
    }

    public void readStudent(Student student) throws Exception{
       // String name = sc.nextLine();
        student.setName(sc.nextLine());
        student.setCourse(sc.nextInt());
        student.setGroup(sc.nextInt());
        sc.nextLine();
    }
}
