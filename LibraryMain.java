import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class LibraryMain {
	private static String DATABASE = "Library.sqlite";
    final static String menu = "MENU\n1. Search\n2. Add new records\n3. Order items\n4. Edit records\n5. Useful reports\n6. Quit";
    public static Scanner keyboard = new Scanner(System.in);
    public static int choice;
    public static List<Artist> artistInventory = new ArrayList<>();
    public static List<Movie> movieInventory = new ArrayList<>();

    /**
     * Connects to the database if it exists, creates it if it does not, and returns the connection object.
     * 
     * @param databaseFileName the database file name
     * @return a connection object to the designated database
     */
    public static Connection initializeDB(String databaseFileName) {
    	/**
    	 * The "Connection String" or "Connection URL".
    	 * 
    	 * "jdbc:sqlite:" is the "subprotocol".
    	 * (If this were a SQL Server database it would be "jdbc:sqlserver:".)
    	 */
        String url = "jdbc:sqlite:" + databaseFileName;
        Connection conn = null; // If you create this variable inside the Try block it will be out of scope
        try {
			//Class.forName("com.sqlite.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url);
            if (conn != null) {
            	// Provides some positive assurance the connection and/or creation was successful.
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The connection to the database was successful.");
            } else {
            	// Provides some feedback in case the connection failed but did not throw an exception.
            	System.out.println("Null Connection");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("There was a problem connecting to the database.");
        }
        return conn;
    }
    
    /**
     * Queries the database and prints the results.
     * 
     * @param conn a connection object
     * @param sql a SQL statement that returns rows
     * This query is written with the Statement class, typically 
     * used for static SQL SELECT statements
     */
    
    public static void sqlQuery(Connection conn, String sql){
        try {
        	Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	ResultSetMetaData rsmd = rs.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i = 1; i <= columnCount; i++) {
        		String value = rsmd.getColumnName(i);
        		System.out.print(value);
        		if (i < columnCount) System.out.print(",  ");
        	}
			System.out.print("\n");
        	while (rs.next()) {
        		for (int i = 1; i <= columnCount; i++) {
        			String columnValue = rs.getString(i);
            		System.out.print(columnValue);
            		if (i < columnCount) System.out.print(",  ");
        		}
    			System.out.print("\n");
        	}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static Connection conn;
    public static void main(String[] args) throws SQLException {

    	
    	conn = initializeDB(DATABASE);
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
                	System.out.println("Goodbye");
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
    private static void addNewRecords() throws SQLException{
        final String recordsMenu = "1. Add an artist\n2. Add a Track";
        System.out.println(recordsMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
        switch (choice) {

        case 1:
        	
            
            break;
        case 2:
            System.out.println("Enter name of Track:");
            String track = keyboard.nextLine();
            //TODO: Replace with prepared statements
            sqlQuery(conn, "SELECT * FROM Track WHERE Name = "+track+";");
            
            break;
        default:
            System.out.println("Invalid Option");
        }
    }
    // 1a. The user provides an artist, the program retrieves the information
    // available
    // 1b. The user provides a track name to search information about it.

    private static void searchRecords() throws SQLException {
        final String searchMenu = "1. Artist\n2. Track";
        System.out.println(searchMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();

      
        switch (choice) {

        case 1:
        	PreparedStatement getArtist=null;
        	ResultSet artistResult= null;
			try {
				getArtist = conn.prepareStatement("SELECT Name FROM Track WHERE ArtistName = ?" );
				System.out.println("Enter name of Artist:");
	            String artist = keyboard.next();
	            getArtist.setString(1,artist);

	            artistResult= getArtist.executeQuery();
	            if(!artistResult.next()) {
	            	System.out.println("No artist found");
	            }else {
	            	System.out.println("Tracks sang by "+artist+":");
	            }
	            while(artistResult.next()){  
	            	System.out.println(artistResult.getString(1));  
	            	} 
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			finally {
				if( artistResult!=null) {artistResult.close();}
				if (getArtist!=null) {getArtist.close();}
			}
            
            break;
        case 2:
        	//TODO: Fix the track info printing out
        	PreparedStatement getTrack=null;
        	ResultSet trackResult=null;
        	try {
        		getTrack=conn.prepareStatement("SELECT * FROM Track WHERE Name = ?" );
        		System.out.println("Enter name of Track:");
        		String track = keyboard.next();
        		getTrack.setString(1, track);
        		trackResult=getTrack.executeQuery();
        		if(!trackResult.next()) {
        			System.out.println("No Track found");
        		}else {
        			System.out.println("Track info:");
        		}

        		while(trackResult.next()){  
	            	String name=trackResult.getString("Name");
	            	String genre=trackResult.getString("Genre");
	            	String artistName=trackResult.getString("ArtistName");
	            	System.out.println("Name: "+name+", Genre: "+ genre+", Artist Name: "+artistName);
	            	} 
        	}
        	catch (SQLException e){
        		e.printStackTrace();
        	}
        	finally {
        		if( trackResult!=null) {trackResult.close();}
				if (getTrack!=null) {getTrack.close();}
        	}
            break;
        default:
            System.out.println("Invalid Option");
        }
    }

}