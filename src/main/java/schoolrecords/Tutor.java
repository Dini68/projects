package schoolrecords;

import java.util.List;

public class Tutor {

    private final String name;
    private final List<Subject> taughtSubjects;

    public Tutor(String name, List<Subject> taughtSubjects) {
        this.name = name;
        this.taughtSubjects = taughtSubjects;
    }

    public String getName() {
        return name;
    }
    public boolean tutorTeachingSubject(Subject subject) {
        for (Subject sb: taughtSubjects) {
            if (sb.getSubjectName().equals(subject.getSubjectName())) {
                return true;
            }
        }
        return false;
    }
}
