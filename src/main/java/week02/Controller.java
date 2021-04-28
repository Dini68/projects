package week02;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {

    private Scanner scanner = new Scanner(System.in);

    private final static List<String> MENUS = new ArrayList<>(List.of(
        "1. Tárgyalók sorrendben",
        "2. Tárgyalók visszafelé sorrendben",
        "3. Minden második tárgyaló",
        "4. Területek",
        "5. Keresés pontos név alapján",
        "6. Keresés névtöredék alapján",
        "7. Keresés terület alapján",
        "8. Kilépés"));

    public static final int MIN_LENGTH_OF_MEETING_ROOM = 2;
    public static final int MIN_WIDTH_OF_MEETING_ROOM = 2;
    public static final int MIN_NUMBER_OF_MEETING_ROOMS = 1;
    private Office office;

    public Office getOffice() {
        return office;
    }

    public static void main(String[] args) {
        Controller controller = new Controller();

        controller.readOffice();
        controller.printMenu();
        controller.runMenu();
    }

    public void readOffice() {
        this.office = new Office();
//        office.addMeetingRoom(new MeetingRoom("Room1", 2, 3));
//        office.addMeetingRoom(new MeetingRoom("Room2", 3, 3));
//        office.addMeetingRoom(new MeetingRoom("Room3", 4, 3));
//        office.addMeetingRoom(new MeetingRoom("Room4", 2, 5));
//
        int number = getNumberOfMeetingRoom();
        for (int i = 0; i < number; i++) {
            String name = getNameOfMeetingRoom(i);
            int width = getWidthOfMeetingRoom(i);
            int length = getLengthOfMeetingRoom(i);
            office.addMeetingRoom(new MeetingRoom(name, length, width));
        }
    }

    private int getNumberOfMeetingRoom() {
        Integer numberOfMeetingRoom;
        do {
            System.out.print("Kérem a tárgyalók számát: ");
            String numberStr = scanner.nextLine();
            numberOfMeetingRoom = getNumber(numberStr);
        } while (numberOfMeetingRoom == null || numberOfMeetingRoom < MIN_NUMBER_OF_MEETING_ROOMS);
        return numberOfMeetingRoom;
    }

    private Integer getNumber(String numberStr) {
        Integer numberOfMeetingRoom = null;
        try {
            numberOfMeetingRoom = Integer.parseInt(numberStr);
            if (numberOfMeetingRoom < MIN_NUMBER_OF_MEETING_ROOMS) {
                System.out.println("A tárgyalók száma nem lehet 1-nél kisebb: " + numberOfMeetingRoom);
            }
            return Integer.parseInt(numberStr);
        } catch (NumberFormatException nfe) {
            System.out.println("Hiba, ez nem szám! '" + numberStr + "'");
        }
        return numberOfMeetingRoom;
    }

    private String getNameOfMeetingRoom(int i) {
        String name;
        do {
            System.out.print((i + 1) + ". tárgyaló neve: ");
            name = scanner.nextLine();
            if (name.isBlank()) {
                System.out.println("A név nem lehet üres!");
            }
            for (MeetingRoom mr: office.getMeetingRooms()) {
                if (mr.getName().equals(name)) {
                    System.out.println("Ez a név már létezik, válassz másikat: " + name);
                    name = "";
                }
            }
        } while (name.isBlank());
        return name;
    }

    private int getLengthOfMeetingRoom(int i) {
        Integer lengthOfMeetingRoom;
        do {
            System.out.print((i + 1) + ". tárgyaló hossza: ");
            String lengthStr = scanner.nextLine();
            lengthOfMeetingRoom = getLength(lengthStr);
        } while (lengthOfMeetingRoom == null || lengthOfMeetingRoom < MIN_LENGTH_OF_MEETING_ROOM);
        return lengthOfMeetingRoom;
    }

    private Integer getLength(String lengthStr) {
        Integer lengthOfMeetingRoom = null;
        try {
            lengthOfMeetingRoom = Integer.parseInt(lengthStr);
            if (lengthOfMeetingRoom < MIN_LENGTH_OF_MEETING_ROOM) {
                System.out.println("A tárgyaló hossza nem lehet 2-nél kisebb: " + lengthOfMeetingRoom);
            }
            return Integer.parseInt(lengthStr);
        } catch (NumberFormatException nfe) {
            System.out.println("Hiba, ez nem szám! '" + lengthStr + "'");
        }
        return lengthOfMeetingRoom;
    }

    private int getWidthOfMeetingRoom(int i) {
        Integer widthOfMeetingRoom;
        do {
            System.out.print((i + 1) + ". tárgyaló szélessége: ");
            String widthStr = scanner.nextLine();
            widthOfMeetingRoom = getWidth(widthStr);
        } while (widthOfMeetingRoom == null || widthOfMeetingRoom < MIN_WIDTH_OF_MEETING_ROOM);
        return widthOfMeetingRoom;
    }

    private Integer getWidth(String widthStr) {
        Integer widthOfMeetingRoom = null;
        try {
            widthOfMeetingRoom = Integer.parseInt(widthStr);
            if (widthOfMeetingRoom < MIN_WIDTH_OF_MEETING_ROOM) {
                System.out.println("A tárgyaló szélesssége nem lehet 2-nél kisebb: " + widthOfMeetingRoom);
            }
            return Integer.parseInt(widthStr);
        } catch (NumberFormatException nfe) {
            System.out.println("Hiba, ez nem szám! '" + widthStr + "'");
        }
        return widthOfMeetingRoom;
    }

    public void printMenu() {
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
            if (List.of("1","2","3","4","5","6","7","8").contains(menuNumberStr)) {
                implementMenuPoint(menuNumberStr);
                if (!menuNumberStr.equals("8")) {
                    printMenu();
                }
            }
            else {
                System.out.println("Nincs ilyen menüpont!");
            }
        } while (!menuNumberStr.equals("8"));
    }

    private void implementMenuPoint(String menuNumberStr) {
        int menuNumber = Integer.parseInt(menuNumberStr) - 1;
        System.out.println("\t" + MENUS.get(menuNumber));
        switch (menuNumberStr) {
            case "1" : {
                office.printNames();
                return;
            }
            case "2" : {
                office.printNamesReverse();
                return;
            }
            case "3" : {
                office.printEvenNames();
                return;
            }
            case "4" : {
                office.printAreas();
                return;
            }
            case "5" : {
                office.printMeetingRoomsWithName(getName());
                return;
            }
            case "6" : {
                office.printMeetingRoomsContains(getPart());
                return;
            }
            case "7" : {
                office.printAreasLargerThan(getArea());
                return;
            }
        }
    }

    private String getName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a tárgyaló nevét: ");
        String name = scanner.nextLine();
        return name;
    }

    private String getPart() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a névtöredéket: ");
        String part = scanner.nextLine();
        return part;
    }

    private int getArea() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Add meg a minimum területet: ");
        String areaStr = scanner.nextLine();
        int area = 0;
        do {
            try {
                area = Integer.parseInt(areaStr);
            } catch (NumberFormatException nfe) {
                System.out.println("Ez nem szám: " + areaStr);
            }
        } while (area == 0);
        return area;
    }
}
//    Készíts egy programot, mely egy irodaházban lévő tárgyalókat tartja nyilván! Először kérd be a felhasználótól,
//    hogy hány darab tárgyalót szeretne rögzíteni. Majd kérd be minden tárgyalóhoz a következő adatokat:
//
//        Tárgyaló neve
//        Tárgyaló szélessége méterben
//        Tárgyaló hosszúsága méterben
//        Majd írj ki a felhasználónak egy menüt, a következőképp:
//
//        1. Tárgyalók sorrendben
//        2. Tárgyalók visszafele sorrendben
//        3. Minden második tárgyaló
//        4. Területek
//        5. Keresés pontos név alapján
//        6. Keresés névtöredék alapján
//        7. Keresés terület alapján
//        Majd kérj be a felhasználótól egy számot! Hajtsd végre a számhoz tartozó funkciót.
//
//        Tárgyalók sorrendben: csak a neveket kell kiírni olyan sorrendben, ahogy a felhasználó beírta
//        Tárgyalók visszafele sorrendben: csak a neveket kell kiírni fordított sorrendben, ahogy a felhasználó beírta
//        Minden második tárgyaló: minden második tárgyaló nevét kell kiírni
//        Területek: ki kell írni minden tárgyaló nevét, szélességét, hosszúságát és területét
//        Keresés pontos név alapján: kérj be a felhasználótól egy nevet, és annak a tárgyalónak írd ki a szélességét,
//        hosszúságát és területét, melynek ez a neve. Ha nincs ilyen nevű, nem kell kiírni semmit.
//        Keresés névtöredék alapján: kérj be a felhasználótól egy névtöredéket,
//        és annak a tárgyalónak írd ki a szélességét, hosszúságát és területét, amelynek
//        a nevében benne van ez a névtöredék! Ne számítson a kis- és nagybetű különbség!
//        Keresés terület alapján: kérj be egy egész számot, és csak azoknak a tárgyalóknak
//        írd ki a nevét, szélességét, hosszúságát és területét, melyeknek a területe nagyobb,
//        mint a felhasználó által beírt terület!
//        Osztályok
//        Dolgozz a week02 csomagba!
//
//        A Controller osztálynak legyen egy main() metódusa, mely példányosít egy Controller példányt,
//        és rendre meghívja annak metódusait.
//
//        A readOffice() példányosít egy Office objektumot, eltárolja az attribútumába.
//        Majd bekéri a tárgyalókat egy ciklusban, MeetingRoom objektumokat példányosít,
//        majd a addMeetingRoom() metódussal beteszi az Office példányba.
//
//        A printMenu() metódus kiírja a menüt.
//
//        A runMenu() bekéri a felhasználótól a menüpont számát, majd egy elágazás alapján meghívja
//        az Office megfelelő metódusát. Ha annak kell paraméter, akkor azt ez a metódus kéri be a felhasználótól.
//
//        Az Office metódusai rendre megvalósítják a leírt funkciókat. Ciklusokkal és feltételekkel dolgoznak.
//
//        A MeetingRoom egy egyszerű osztály, három attribútummal.
//        Van egy getArea() metódusa, mely kiszámolja a tárgyaló területét.