package schoolrecords;

public class StudyResultByName {

    private final String studentName;
    private final double studyAverage;

    public StudyResultByName(String studentName, double studyAverage) {
        this.studentName = studentName;
        this.studyAverage = studyAverage;
    }

    public double getStudyAverage() {
        return studyAverage;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString() {
        return studentName + " " + studyAverage;
    }
}
