import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;

public class LibraryMain{

    final static String menu = "1. Search\n2. Add new records\n3. Order items\n4. Edit records\n5. Useful reports\n 6. Quit";
    public HashMap<String,List<String>> inventory= new HashMap<>();

    public static void main(String[] args) {
        int choice = 0;
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

    private static void usefulReports() {
    }

    private static void editRecords() {
    }

    private static void orderItems() {
    }

    private static void addNewRecords() {
    }

    private static void searchRecords() {
    }

}