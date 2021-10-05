import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryMain {

    final static String menu = "1. Search\n2. Add new records\n3. Order items\n4. Edit records\n5. Useful reports\n 6. Quit";
    

    public static void main(String[] args) {
        int choice = 0;
        List<ArtistOjects> artistInventory = new ArrayList<>();
        List<MovieOjects> movieInventory = new ArrayList<>();
        Scanner keyboard = new Scanner(System.in);
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
    }

    // User selects an artist (provide the name), edit any field of the artist and
    // then save it, updating your internal structure that storage the artist.
    private static void editRecords() {
    }

    // The user enter the information to order a new movie, with number of copies
    // purchase, price and an estimated date of arrival
    private static void orderItems() {
    }

    // The user provides all the info needed to enter a new artist or a new Song
    // (track). Use the attributes that you defined in your relational schema
    private static void addNewRecords() {
    }
    // 1a. The user provides an artist, the program retrieves the information
    // available
    // 1b. The user provides a track name to search information about it.

    private static void searchRecords() {
    }

}