package schoolrecords;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private final String name;
    private final List<Mark> marks = new ArrayList<>();

    public List<Mark> getMarks() {
        return marks;
    }

    public Student(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Student name must not be empty!");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double calculateAverage() {
        double sum = 0;
        for (Mark mark: marks) {
            sum += mark.getMarkType().getValue();
        }
        return Math.round(100 * sum / marks.size()) / 100.0;
    }

    public double calculateSubjectAverage(Subject subject) {
        double sum = 0;
        int count = 0;
        for (Mark mark: marks) {
            if (mark.getSubject().getSubjectName().equals(subject.getSubjectName())) {
                sum += mark.getMarkType().getValue();
                count ++;
            }
        }
        return Math.round(100 * sum / count )/ 100.0;
    }

    public void grading(Mark mark) {
        if (mark == null) {
            throw new NullPointerException("Mark must not be null!");
        }
        marks.add(mark);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" marks: ");
        for (Mark mark: marks) {
            sb.append(mark.getSubject().getSubjectName()).append(": ");
            sb.append(mark.toString());
        }
        return sb.toString();
    }
}
