package osu.cse3241;

import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * <h1>CSE3241 Introduction to Database Systems - Sample Java application.</h1>
 * 
 * <p>Sample app to be used as guidance and a foundation for students of 
 * CSE3241 Introduction to Database Systems at 
 * The Ohio State University.</p>
 * 
 * <h2>!!! - Vulnerable to SQL injection - !!!</h2>
 * <p>Correct the code so that it is not 
 * vulnerable to a SQL injection attack. ("Parameter substitution" is the usual way to do this.)</p>
 * 
 * <p>Class is written in Java SE 8 and in a procedural style. Implement a constructor if you build this app out in OOP style.</p>
 * <p>Modify and extend this app as necessary for your project.</p>
 *
 * <h2>Language Documentation:</h2>
 * <ul>
 * <li><a href="https://docs.oracle.com/javase/8/docs/">Java SE 8</a></li>
 * <li><a href="https://docs.oracle.com/javase/8/docs/api/">Java SE 8 API</a></li>
 * <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/">Java JDBC API</a></li>
 * <li><a href="https://www.sqlite.org/docs.html">SQLite</a></li>
 * <li><a href="http://www.sqlitetutorial.net/sqlite-java/">SQLite Java Tutorial</a></li>
 * </ul>
 *
 * <h2>MIT License</h2>
 *
 * <em>Copyright (c) 2019 Leon J. Madrid, Jeff Hachtel</em>
 * 
 * <p>Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.</p>
 *
 * 
 * @author Leon J. Madrid (madrid.1), Jeff Hachtel (hachtel.5)
 * 
 */

public class CSE3241app {
    
	/**
	 *  The database file name.
	 *  
	 *  Make sure the database file is in the root folder of the project if you only provide the name and extension.
	 *  
	 *  Otherwise, you will need to provide an absolute path from your C: drive or a relative path from the folder this class is in.
	 */
	private static String DATABASE = "Library.sqlite";
	
	
	/**
	 *  The query statement to be executed.
	 *  
	 *  Remember to include the semicolon at the end of the statement string.
	 *  (Not all programming languages and/or packages require the semicolon (e.g., Python's SQLite3 library))
	 */
	private static String sqlStatement = "SELECT * FROM Media_item WHERE Album_Flag = 1;";
	
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
     * This query is written with the Statement class, tipically 
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

    final static String menu = "Main Menu\n1. Search\n2. Add new records\n3. Order items\n4. Edit records\n5. Useful reports\n6. Quit";
    public static Scanner keyboard = new Scanner(System.in);
    public static Connection conn;
    public static void main(String[] args) {
    	
    	conn = initializeDB(DATABASE);
    	//sqlQuery(conn, sqlStatement);
    	
    	int choice = 1;
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
        final String reportMenu = "Reports Menu\n1. Tracks by ARTIST released before YEAR\n"
                + "2. Number of albums checked out by a single patron\n" + "3. Most popular actor in the database\n"
                + "4. Most listened to artist in the database\n" + "5. Patron who has checked out the most videos";
        System.out.println(reportMenu);
        System.out.println("Enter number option would you like?");
        int choice = keyboard.nextInt();
		//TODO: Implement
    }

    // User selects an artist (provide the name), edit any field of the artist and
    // then save it, updating your internal structure that storage the artist.
    private static void editRecords() {
        final String editMenu = "Record Menu\n1. Edit an Artist";
        System.out.println(editMenu);
        System.out.println("Enter number option would you like?");
        int choice = keyboard.nextInt();
        switch (choice){
            case 1:
				System.out.println("Which artist do you want to edit?");
				String artistString = keyboard.nextLine();
				boolean found = false;
				//TODO: Use 'UPDATE' statements for these
                if (!found) {
                    System.out.println("No Artist found with that name");
                }else{
					System.out.println("What would you like to edit about the artist?\n1. Name\n2. Age\n3. Genres");
					int editChoice = keyboard.nextInt();
					switch (editChoice){
						case 1:
							System.out.println("Enter new name:");
							String name = keyboard.nextLine();
							//TODO: Set name
							break;
						case 2:
							System.out.println("Enter new age:");
							int age = keyboard.nextInt();
							//TODO: Set age
							break;
						case 3:
							System.out.println("Enter new genre:");
							String genre = keyboard.nextLine();
							//TODO: Add genre
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
        final String orderMenu = "Order Menu\n1. Order a Movie\n2. Activate item recieved";
        System.out.println(orderMenu);
        System.out.println("Enter number option would you like?");
        int choice = keyboard.nextInt();
        switch (choice){
            case 1:
				System.out.println("What movie do you want to order?");
				String movieName = keyboard.nextLine();
				boolean found = false;
				//TODO: Find movie
				// SELECT * FROM Media_Item WHERE MovieFlag = 1 AND Name = [movieName];
                if (!found) {
                    System.out.println("No Movie found with that name");
                }else{
						System.out.println("How many copies do you want?");
						int copies = keyboard.nextInt();
						//TODO: Get [copies] copies of movie
						System.out.println("Your order will arive in one business week."); //TODO: maybe implement date system?
				}
                break;
            case 2:
				//TODO: Activate item received
                break;
            default:
                System.out.println("Invalid Option");
        }
    }

    // The user provides all the info needed to enter a new artist or a new Song
    // (track). Use the attributes that you defined in your relational schema
    private static void addNewRecords() {
        final String recordsMenu = "New Record Menu\n1. Add an artist\n2. Add a Track";
        System.out.println(recordsMenu);
        System.out.println("Enter number option would you like?");
        int choice = keyboard.nextInt();
        switch (choice){
            case 1:
				System.out.println("Name of artist:");
				String artistName = keyboard.nextLine();
				System.out.println("Age of artist:");
				int artistAge = keyboard.nextInt();
				//TODO: Determine if 'age' is needed and, if not, remove it
				//TODO: Replace with prepared statements
				sqlQuery(conn, "INSERT INTO Artist VALUES ("+artistName+");");
                break;
            case 2:
				System.out.println("Name of track:");
				String trackName = keyboard.nextLine();
				System.out.println("Genre of track:");
				String trackGenre = keyboard.nextLine();
				System.out.println("Age of length:");
				int trackLength = keyboard.nextInt();
				System.out.println("Year of length:");
				int trackYear = keyboard.nextInt();
				artistName = "";
				//TODO: Replace length/year with artist name
				//TODO: Replace with prepared statements
				sqlQuery(conn, "INSERT INTO Track VALUES ("+trackName+", "+trackGenre+", "+artistName+");");
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
        final String searchMenu = "Search Menu\n1. Artist\n2. Track";
        System.out.println(searchMenu);
        System.out.println("Enter number option would you like?");
        int choice = keyboard.nextInt();
        boolean found = false;
        switch (choice) {

            case 1:
                System.out.println("Enter name of Artist:");
                String artist = keyboard.nextLine();
                //TODO: Replace with prepared statements
                sqlQuery(conn, "SELECT * FROM Artist WHERE Name = "+artist+";");
                if (!found) {
                    System.out.println("No Artist found with that name");
                }
                break;
            case 2:
                System.out.println("Enter name of Track:");
                String track = keyboard.nextLine();
                //TODO: Replace with prepared statements
                sqlQuery(conn, "SELECT * FROM Track WHERE Name = "+track+";");
                if (!found) {
                    System.out.println("No Track found with that name");
                }
                break;
            default:
                System.out.println("Invalid Option");
        }
    }
}
