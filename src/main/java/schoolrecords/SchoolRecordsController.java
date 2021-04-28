package schoolrecords;

import java.util.*;

public class SchoolRecordsController {

    private final ClassRecords classRecords = new ClassRecords("5.a", new Random());
    private final List<Subject> subjects = new ArrayList<>();
    private final List<Tutor> tutors = new ArrayList<>();

    private final Scanner scanner = new Scanner(System.in);

    private final static List<String> MENUS = new ArrayList<>(List.of(
            "1. Diákok nevének listázása",
            "2. Diák név alapján keresése",
            "3. Diák létrehozása",
            "4. Diák név alapján törlése",
            "5. Diák feleltetése",
            "6. Osztályátlag kiszámolása",
            "7. Tantárgyi átlag kiszámolása",
            "8. Diákok átlagának megjelenítése",
            "9. Diák átlagának kiírása",
            "10. Diák tantárgyhoz tartozó átlagának kiírása",
            "11. Kilépés" ));

    public void initSchool() {
        subjects.add(new Subject("matematika"));
        subjects.add(new Subject("földrajz"));
        subjects.add(new Subject("biológia"));
        subjects.add(new Subject("zene"));
        subjects.add(new Subject("fizika"));
        subjects.add(new Subject("kémia"));
        subjects.add(new Subject("magyar"));
        subjects.add(new Subject("történelem"));
        subjects.add(new Subject("testnevelés"));
        subjects.add(new Subject("informatika"));
        subjects.add(new Subject("angol"));

        tutors.add(new Tutor("Nagy Csilla",
                Arrays.asList(new Subject("matematika"), new Subject("fizika"))));
        tutors.add(new Tutor("Kovács Rita",
                Arrays.asList(new Subject("földrajz"), new Subject("zene"))));
        tutors.add(new Tutor("Varga Márton",
                Arrays.asList(new Subject("biológia"), new Subject("kémia"))));
        tutors.add(new Tutor("Nagy Béla",
                Arrays.asList(new Subject("matematika"), new Subject("testnevelés"))));
        tutors.add(new Tutor("Jókai Ferenc",
                Arrays.asList(new Subject("magyar"), new Subject("történelem"))));
        tutors.add(new Tutor("Neumann János",
                Arrays.asList(new Subject("angol"), new Subject("informatika"))));
    }

    public static void main(String[] args) {
        SchoolRecordsController sc = new SchoolRecordsController();
        sc.initSchool();
        sc.printMenu();
        sc.runMenu();
    }

    private void printMenu() {
        System.out.println("\n\t\t>>  Menü  <<");
        for (String s: MENUS) {
            System.out.println(s);
        }
    }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        String menuNumberStr;
        do {
            System.out.print("Menüpont száma: ");
            menuNumberStr= scanner.nextLine();
            if (List.of("1","2","3","4","5","6","7","8","9","10","11").contains(menuNumberStr)) {
                implementMenuPoint(menuNumberStr);
                if (!menuNumberStr.equals("11")) {
                    printMenu();
                }
            }
            else {
                System.out.println("Nincs ilyen menüpont!");
            }
        } while (!menuNumberStr.equals("11"));
    }

    private void implementMenuPoint(String menuNumberStr) {
        int menuNumber = Integer.parseInt(menuNumberStr) - 1;
        System.out.println("\t" + MENUS.get(menuNumber));
        switch (menuNumberStr) {
            case "1" : {
                System.out.println(classRecords.listStudentNames());
                return;
            }
            case "2" : {
                System.out.println(classRecords.findStudentByName(actName()));
                return;
            }
            case "3" : {
                if (classRecords.addStudent(new Student(actName()))) {
                    System.out.println("Sikerült felvenni a nevet!");
                } else {
                    System.out.println("Hiba! Már van ilyen név a diákok között!");
                }
                return;
            }
            case "4" : {
                if (classRecords.removeStudent(new Student(actName()))) {
                    System.out.println("Sikerült törölni a nevet!");
                } else {
                    System.out.println("Hiba! Nincs ilyen név a diákok között!");
                }
                return;
            }
            case "5" : {
                repetition();
                return;
            }
            case "6" : {
                System.out.println(classRecords.calculateClassAverage());
                return;
            }
            case "7" : {
                System.out.println(classRecords.calculateClassAverageBySubject(getSubject()));
                return;
            }
            case "8" : {
                System.out.println(classRecords.listStudyResults());
                return;
            }
            case "9" : {
                System.out.println(classRecords.findStudentByName(actName()).calculateAverage());
                return;
            }
            case "10" : {
                String actStudentName = actName();
                Subject actSubjectName = getSubject();
                System.out.println(classRecords.findStudentByName(actStudentName).calculateSubjectAverage(actSubjectName));
            }
        }
    }

    private String actName() {
        System.out.print("Kérem a diák nevét: ");
        String name;
        do {
            name = scanner.nextLine();
        } while (name.isBlank());
        return name;
    }

    private void repetition() {
        Student student = classRecords.repetition();
        System.out.println(student.getName() + " felel");
        MarkType markType = getMark();
        Subject subject = getSubject();
        Tutor tutor = getTutor(subject);
        Mark mark = new Mark(markType, subject, tutor);
        student.grading(mark);
        System.out.println(student.getName() + ": " + subject.getSubjectName() + ": "
                + markType.getValue());
    }

    private Tutor getTutor(Subject subject) {
        do {
            System.out.print("Kérem a tanár nevét: ");
            String tutorName = scanner.nextLine();
            Tutor tutor = getTutorByName(tutorName);
            if (tutor != null) {
                if (tutor.tutorTeachingSubject(subject)) {
                    return tutor;
                }
                System.out.println("Hiba! " + tutorName + " nem tanítja az adott tárgyat!");
            }
        } while (true);
    }

    private Tutor getTutorByName(String tutorName) {
        for (Tutor t: tutors) {
            if (t.getName().equals(tutorName)) {
                return t;
            }
        }
        System.out.println("Nincs ilyen név a tanárok között: '" + tutorName + "'");
        return null;
    }

    private Subject getSubject() {
        do {
            System.out.print("Kérem a tantárgyat: ");
            String subjectName = scanner.nextLine();
            for (Subject sb : subjects) {
                if (sb.getSubjectName().equals(subjectName)) {
                    return sb;
                }
            }
            System.out.println("Hiba! Nincs ilyen tantárgy!");
        } while (true);
    }

    private MarkType getMark() {
        do {
            System.out.print("Kérem a jegyet: ");
            String markStr = scanner.nextLine();
            if ("12345".contains(markStr) && markStr.length() == 1) {
                return MarkType.values()[5 - Integer.parseInt(markStr)];
            }
            else{
                System.out.println("Az érdemjegy 1..5 lehet!");
            }
        } while (true);
    }
}
