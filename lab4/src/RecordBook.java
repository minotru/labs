import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecordBook {
    public ArrayList<Session> sessions;
    public final Student student;

    public RecordBook() {
        this(new Student());
    }

    public RecordBook(Student student) {
        this.student = student;
        this.sessions = new ArrayList<Session>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(student + "\n");
        for (Session session : sessions)
            sb.append(session);
        return sb.toString();
    }

    static public class Session {
        public final ArrayList<Exam> exams;
        public final ArrayList<Credit> credits;
        private final int number;

        public Session(int number) throws IllegalArgumentException {
            this.exams = new ArrayList<Exam>();
            this.credits = new ArrayList<Credit>();
            if (number < 1 || number > 9)
                throw new IllegalArgumentException("Number of session must be in 1..9");
            this.number = number;
        }

        public Session(Session other) {
            this.exams = new ArrayList<Exam>(other.exams);
            this.credits = new ArrayList<Credit>(other.credits);
            number = other.number;
        }

        public int getNumber() {
            return number;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Session " + number + ":\n");
            sb.append("Exams:\n");
            for (Exam exam : exams)
                sb.append(exam.toString() + '\n');
            sb.append("Credits:\n");
            for (Credit credit : credits)
                sb.append(credit.toString() + '\n');
            return sb.toString();
        }

        static private class Record {
            private String subject;
            private String teacher;
            
            public Record() {
                this("", "");
            }
            
            public Record(String subject, String teacher) {
                setSubject(subject);
                setTeacher(teacher);
            }

            public Record(Record other) {
                this(other.subject, other.teacher);
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String getSubject() {
                return this.subject;
            }

            public void setTeacher(String teacher) {
                this.teacher = teacher;
            }

            public String getTeacher() {
                return this.teacher;
            }

            @Override
            public String toString() {
                return subject + " " + teacher;
            }
        }
        
        static public class Exam extends Record{
            private int mark;
            
            public Exam() throws Exception{
                this("", "", 0);
            }

            public Exam(Exam other) {
                super(other);
                this.mark = other.mark;
            }
    
            public Exam(String subject, String teacher, int mark) throws Exception{
                super(subject, teacher);
                setMark(mark);
            }
    
            public void setMark(int mark) throws Exception{
                if (mark < 0 || mark > 10)
                    throw new Exception("Invalid mark.");
                this.mark = mark;
            }

            public int getMark() {
                return mark;
            }

            @Override
            public String toString() {
                return super.toString() + " " + mark;
            }
        }
        
        static public class Credit extends Record {
            boolean passed;

            public Credit() {
                this("", "", false);
            }
            
            public Credit(Credit other) {
                super(other);
                passed = other.passed;
            }

            public Credit(String subject, String teacher, boolean passed) {
                super(subject, teacher);
                setPassed(passed);
            }

            public void setPassed(boolean passed) {
                this.passed = passed;
            }

            public boolean hasPassed() {
                return this.passed;
            }

            @Override
            public String toString() {
                return super.toString() + " " + (hasPassed() ? "passed" : "failed");
            }
        }
    }
}