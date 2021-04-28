package week02;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Office {

    private List<MeetingRoom> meetingRooms = new ArrayList<>();

    public List<MeetingRoom> getMeetingRooms() {
        return meetingRooms;
    }

    public void addMeetingRoom(MeetingRoom meetingRoom) {
        meetingRooms.add(meetingRoom);
    }

    public void printNames() {
        for (MeetingRoom s: getMeetingRooms()) {
            System.out.println(s.getName());
        }
    }

    public void printNamesReverse() {
        for (int i = getMeetingRooms().size() - 1; i >= 0; i--) {
            System.out.println(getMeetingRooms().get(i).getName());
        }
    }

    public void printEvenNames() {
        for (int i = 0; i < getMeetingRooms().size(); i++) {
            if (i % 2 == 1) {
                System.out.println(getMeetingRooms().get(i).getName());
            }
        }
    }

    public void printAreas() {
        for (MeetingRoom s: getMeetingRooms()) {
            System.out.println(s.toString() + " t=" + s.getArea());
        }
    }

    public void printMeetingRoomsWithName(String name) {
        for (MeetingRoom s: getMeetingRooms()) {
            if (s.getName().equals(name)) {
                System.out.println(s.toString() + " t=" + s.getArea());
                return;
            }
        }
        System.out.println("Nincs ilyen nevű tárgyaló: '" + name + "'");
    }

    public void printMeetingRoomsContains(String part) {
        boolean found = false;
        for (MeetingRoom s: getMeetingRooms()) {
            if (s.getName().toLowerCase().contains(part.toLowerCase())) {
                System.out.println(s.toString() + " t=" + s.getArea());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nincs ilyen névtöredéket tartalmazó tárgyaló: '" + part + "'");
        }
    }
    public void printAreasLargerThan(int area) {
        for (MeetingRoom s: getMeetingRooms()) {
            if (s.getArea() > area) {
                System.out.println(s.toString() + " t=" + s.getArea());
            }
        }
    }

}
