package schoolrecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassRecords {

    private final String className;
    private final Random rnd;
    private final List<Student> students = new ArrayList<>();

    public String getClassName() {
        return className;
    }

    public ClassRecords(String className, Random rnd) {
        this.className = className;
        this.rnd = rnd;
    }

    public boolean isEmpty(String str) {
        return str == null || str.isBlank();
    }
    public boolean addStudent(Student student) {
        for (Student st: students) {
            if (st.getName().equals(student.getName())) {
                return false;
            }
        }
        return students.add(student);
    }

    public boolean removeStudent(Student student) {
        Student foundStudent = null;
        for (Student st: students) {
            if (st.getName().equals(student.getName())) {
                foundStudent = st;
            }
        }
        if (foundStudent == null) return false;
        return students.remove(foundStudent);
    }

    public double calculateClassAverage() {
        if (students.size() == 0) {
            throw new ArithmeticException("No student in the class, average calculation aborted!");
        }

        boolean foundMark = false;
        int i = 0;
        do {
            if (students.get(i).getMarks().size() > 0) {
                foundMark = true;
            }
            i ++;
        } while (!foundMark && i != students.size());


        if (!foundMark) {
            throw new ArithmeticException("No marks present, average calculation aborted!");
        }
        double average;
        double sum = 0;
        int count = 0;
        for (Student st: students) {
            for (Mark mark: st.getMarks()) {
                sum += mark.getMarkType().getValue();
                count ++;
            }
        }
        average = Math.round(100 * sum / count) / 100.0;
        return average;
    }

    public double calculateClassAverageBySubject(Subject subject) {
        double average;
        double sum = 0;
        int count = 0;
        for (Student st: students) {
            average = st.calculateSubjectAverage(subject);
            sum += average;
            if (Math.abs(average) > 0.0000001) {
                count ++;
            }
        }
        average = Math.round(100 * sum / count) / 100.0;
        return average;
    }

    public Student findStudentByName(String name) {
        if (isEmpty(name)) {
            throw new IllegalArgumentException("Student name must not be empty!");
        }
        if (students.size() == 0) {
            throw new IllegalStateException("No students to search!");

        }
        for (Student st: students) {
            if (st.getName().equals(name)) {
                return st;
            }
        }
        throw new IllegalArgumentException("Student by this name cannot be found! Kiss Rita");
    }
    public Student repetition() {
        if (students.size() == 0) {
            throw new IllegalStateException("No students to select for repetition!");

        }
        return students.get(rnd.nextInt(students.size()));
    }

    public List<StudyResultByName> listStudyResults() {
        List<StudyResultByName> result = new ArrayList<>();
        for (Student st: students) {
            result.add(new StudyResultByName(st.getName(), st.calculateAverage()));
        }
        return result;
    }

    public String listStudentNames() {
        StringBuilder sb = new StringBuilder();
        for (Student st: students) {
            if (!sb.toString().equals("")) {
                sb.append(", ");
            }
            sb.append(st.getName());
        }
        return sb.toString();
    }
}
