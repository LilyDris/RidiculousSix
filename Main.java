import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    final static String menu = "\nMain Menu\n1. Search\n2. Add new records\n3. Order items\n4. Edit records\n5. Useful reports\n6. Quit";
    public static Scanner keyboard = new Scanner(System.in);
    public static int choice;
    public static List<Artist> artistInventory = new ArrayList<>();
    public static List<Movie> movieInventory = new ArrayList<>();

    public static void main(String[] args) {

        while (choice != 6) {
            System.out.println(menu);
            System.out.println("Enter number option would you like?");
            choice = keyboard.nextInt();
            keyboard.nextLine();
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
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
        keyboard.close();
    }

    // Displays useful reports the user might need
    private static void usefulReports() {
        final String reportMenu = "\nReports Menu\n1. Tracks by ARTIST released before YEAR\n"
                + "2. Number of albums checked out by a single patron\n" + "3. Most popular actor in the database\n"
                + "4. Most listened to artist in the database\n" + "5. Patron who has checked out the most videos";
        System.out.println(reportMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
        keyboard.nextLine();
        // TODO: Rest not to be implemented this checkpoint (3)
    }

    // User selects an artist (provide the name), edit any field of the artist and
    // then save it, updating your internal structure that storage the artist.
    private static void editRecords() {
        final String editMenu = "\nRecord Menu\n1. Edit an Artist";
        System.out.println(editMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
        keyboard.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Which artist do you want to edit?");
                String artistString = keyboard.nextLine();
                Artist artist = new Artist("", null, null);
                boolean found = false;
                for (int i = 0; i < artistInventory.size(); i++) {
                    if (artistString.compareToIgnoreCase(artistInventory.get(i).getName()) == 0) {
                        artist = artistInventory.get(i);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No Artist found with that name");
                } else {
                    System.out.println("What would you like to edit about the artist?\n1. Name\n2. Genres");
                    int editChoice = keyboard.nextInt();
                    keyboard.nextLine();
                    switch (editChoice) {
                        case 1:
                            System.out.println("Enter new name:");
                            String name = keyboard.nextLine();
                            artist.setName(name);
                            break;
                        case 2:
                            System.out.println("Enter new genre:");
                            String genre = keyboard.nextLine();
                            artist.addGenre(genre);
                            break;
                        default:
                            System.out.println("Invalid Option");
                    }
                }
                break;
            default:
                System.out.println("Invalid Option");
        }
    }

    // The user enter the information to order a new movie, with number of copies
    // purchase, price and an estimated date of arrival
    private static void orderItems() {
        final String orderMenu = "\nOrder Menu\n1. Order a Movie\n2. Activate item recieved";
        System.out.println(orderMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
        keyboard.nextLine();
        switch (choice) {
            case 1:
                System.out.println("What movie do you want to order?");
                String movieName = keyboard.nextLine();
                Movie movie = new Movie("", "", 0, 0, 0.0, null);
                boolean found = false;
                for (int i = 0; i < movieInventory.size(); i++) {
                    if (movieName.compareToIgnoreCase(movieInventory.get(i).getName()) == 0) {
                        movie = movieInventory.get(i);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No Movie found with that name");
                } else {
                    System.out.println("How many copies do you want?");
                    int copies = keyboard.nextInt();
                    keyboard.nextLine();
                    int price = copies * movie.getPrice();
                    System.out.println(copies + " copies of " + movie.getName() + " will cost $" + price + ".00");
                    System.out.println("Your order will arive in one business week.");
                    // TODO: maybe implement date system?
                }
                break;
            case 2:
                // TODO: Not to be implemented this checkpoint (3)
                break;
            default:
                System.out.println("Invalid Option");
        }
    }

    // The user provides all the info needed to enter a new artist or a new Song
    // (track). Use the attributes that you defined in your relational schema
    private static void addNewRecords() {
        final String recordsMenu = "\nNew Record Menu\n1. Add an artist\n2. Add a Track";
        System.out.println(recordsMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
        keyboard.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Name of artist:");
                String artistName = keyboard.nextLine();
                System.out.println("Enter exactly 1 genre the artist performs:");
                String genre = keyboard.nextLine();
                List<String> artistGenres = new ArrayList<>();
                artistGenres.add(genre);
                List<Track> artistTracks = new ArrayList<>();
                Artist newArtist = new Artist(artistName, artistGenres, artistTracks);
                artistInventory.add(newArtist);
                break;
            case 2:
                System.out.println("Artist of track:");
                String trackArtist = keyboard.nextLine();
                Artist artist = null;
                for (int i = 0; i < artistInventory.size(); i++) {
                    if (trackArtist.compareToIgnoreCase(artistInventory.get(i).getName()) == 0) {
                        artist = artistInventory.get(i);
                    }
                }
                if (artist == null) {
                    System.out.println("No such artist exists. Please add the artist first.");
                } else {
                    System.out.println("Name of track:");
                    String trackName = keyboard.nextLine();
                    System.out.println("Genre of track:");
                    String trackGenre = keyboard.nextLine();
                    Track newTrack = new Track(trackName, trackGenre, trackArtist);
                    artist.addTrack(newTrack);
                }
                break;
            default:
                System.out.println("Invalid Option");
                break;
        }

    }
    // 1a. The user provides an artist, the program retrieves the information
    // available
    // 1b. The user provides a track name to search information about it.

    private static void searchRecords() {
        final String searchMenu = "\nSearch Menu\n1. Artist\n2. Track";
        System.out.println(searchMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
        keyboard.nextLine();
        boolean found = false;
        switch (choice) {

            case 1:
                System.out.println("Enter name of Artist:");
                String artist = keyboard.nextLine();

                for (int i = 0; i < artistInventory.size(); i++) {
                    if (artist.compareToIgnoreCase(artistInventory.get(i).getName()) == 0) {
                        artistInventory.get(i).printArtistInfo();
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No Artist found with that name");
                }
                break;
            case 2:
                System.out.println("Enter name of Track:");
                String track = keyboard.nextLine();

                for (int i = 0; i < artistInventory.size(); i++) {
                    for (int j = 0; j < artistInventory.get(i).getTracks().size(); j++) {
                        if (track.compareToIgnoreCase(artistInventory.get(i).getTracks().get(j).getTrackName()) == 0) {
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