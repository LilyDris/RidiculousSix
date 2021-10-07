import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryMain {

    final static String menu = "1. Search\n2. Add new records\n3. Order items\n4. Edit records\n5. Useful reports\n6. Quit";
    public static Scanner keyboard = new Scanner(System.in);
    public static int choice;
    public static List<ArtistOjects> artistInventory = new ArrayList<>();
    public static List<MovieObjects> movieInventory = new ArrayList<>();

    public static void main(String[] args) {

        while (choice != 6) {
            System.out.println(menu);
            System.out.println("Enter number option would you like?");
            choice = keyboard.nextInt();
            switch (choice) {
                case 1:
                    searchRecords();
                    break;
                case 2:
                    addNewRecords();
                    break;
                case 3:
                    orderItems();
                    break;
                case 4:
                    editRecords();
                    break;
                case 5:
                    usefulReports();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
        keyboard.close();
    }

    // Displays useful reports the user might need
    private static void usefulReports() {
        final String reportMenu = "1. Tracks by ARTIST released before YEAR\n"
                + "2. Number of albums checked out by a single patron\n" + "3. Most popular actor in the database\n"
                + "4. Most listened to artist in the database\n" + "5. Patron who has checked out the most videos";
        System.out.println(reportMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
    }

    // User selects an artist (provide the name), edit any field of the artist and
    // then save it, updating your internal structure that storage the artist.
    private static void editRecords() {
        final String editMenu = "1. Edit an Artist";
        System.out.println(editMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
    }

    // The user enter the information to order a new movie, with number of copies
    // purchase, price and an estimated date of arrival
    private static void orderItems() {
        final String orderMenu = "1. Order a Movie\n2. Activate item recieved";
        System.out.println(orderMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
    }

    // The user provides all the info needed to enter a new artist or a new Song
    // (track). Use the attributes that you defined in your relational schema
    private static void addNewRecords() {
        final String recordsMenu = "1. Add an artist\n2. Add a Track";
        System.out.println(recordsMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
    }
    // 1a. The user provides an artist, the program retrieves the information
    // available
    // 1b. The user provides a track name to search information about it.

    private static void searchRecords() {
        final String searchMenu = "1. Artist\n2. Track";
        System.out.println(searchMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
        boolean found = false;
        switch (choice) {

            case 1:
                System.out.println("Enter name of Artist:");
                String artist = keyboard.nextLine();

                for (int i = 0; i < artistInventory.size(); i++) {
                    if (1 == artist.compareToIgnoreCase(artistInventory.get(i).getName())) {
                        artistInventory.get(i).printArtistInfo();
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No Artist found with that name");
                }
                break;
            case 2:
                System.out.println("Enter name of Artist:");
                String track = keyboard.nextLine();

                for (int i = 0; i < artistInventory.size(); i++) {
                    for (int j = 0; j < artistInventory.get(i).getTracks().size(); j++) {
                        if (1 == track.compareToIgnoreCase(artistInventory.get(i).getTracks().get(j).getTrackName())) {
                            artistInventory.get(i).getTracks().get(j).printTrackInfo();
                            found = true;
                        }
                    }
                }
                if (!found) {
                    System.out.println("No Track found with that name");
                }
                break;
            default:
                System.out.println("Invalid Option");
        }
    }

}