package timesheet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Company {

    public static final String PLUS_DIGIT_TO_MONTH = "0";
    private final List<Employee> employees = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();
    private final List<TimeSheetItem> timeSheetItems = new ArrayList<>();

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public Company(InputStream employeesFile, InputStream projectsFile) {
        readEmployeesFromFile(employeesFile);
        readProjectsFromFile(projectsFile);
    }

    private void readProjectsFromFile(InputStream projectsFile) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(projectsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                projects.add(new Project(line));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file", ioe);
        }
    }

    private void readEmployeesFromFile(InputStream employeesFile) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(employeesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String firstName = parts[0];
                String lastName = parts[1];
                employees.add(new Employee(firstName, lastName));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file", ioe);
        }
    }
    public void addTimeSheetItem(Employee employee, Project project, LocalDateTime beginDate,
                                 LocalDateTime endDate) {

        timeSheetItems.add(new TimeSheetItem(employee, project, beginDate, endDate));
    }

    public List<ReportLine> calculateProjectByNameYearMonth(String employeeName, int year, int month) {
        checkEmployeeName(employeeName);
        List<ReportLine> result = initReportLines();
        for (TimeSheetItem ts:timeSheetItems) {
            if (ts.getEmployee().getName().equals(employeeName) && ts.getBeginDate().getYear() == year &&
                    ts.getBeginDate().getMonthValue() == month ) {
                addTimeResult(result, ts);
            }
        }
        return result;
    }

    private void addTimeResult(List<ReportLine> result, TimeSheetItem ts) {
        int i = 0;
        for (Project p: projects) {
            if (p.getName().equals(ts.getProject().getName())) {
                result.get(i).addTime(ts.hoursBetweenDates());
            }
            i ++;
        }
    }

    private List<ReportLine> initReportLines() {
        List<ReportLine> result = new ArrayList<>();
        for (Project p: projects) {
            result.add(new ReportLine(p, 0L));
        }
        return result;
    }

    private void checkEmployeeName(String employeeName) {
        boolean found = false;
        for (Employee em: employees) {
            if (em.getName().equals(employeeName)) {
                found = true;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("No such employee name");
        }
    }

    public void printToFile (String employeeName, int year, int month, Path file) {
        List <ReportLine> reportLines = calculateProjectByNameYearMonth(employeeName, year, month);
        StringBuilder sbProjects = new StringBuilder();
        int allTime = 0;
        for (ReportLine rl: reportLines) {
            if (rl.getTime() > 0) {
                allTime += rl.getTime();
                sbProjects.append(rl.getProject().getName()).append("\t")
                        .append(rl.getTime()).append("\n");
            }
        }
        StringBuilder sbHeader = new StringBuilder();
        sbHeader.append(employeeName).append("\t").append(year).append("-");
        if (month < 10) {
            sbHeader.append(PLUS_DIGIT_TO_MONTH);
        }
        sbHeader.append(month).append("\t").append(allTime).append("\n");

        try {
            Files.writeString(file, sbHeader.toString() + sbProjects.toString());
        } catch (IOException ioException) {
            throw new IllegalStateException("can not read");
        }

    }
}
