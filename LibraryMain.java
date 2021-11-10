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
    private static void usefulReports() throws SQLException {
        final String reportMenu = "1. Tracks by Billy Joel released before 2000\n"
                + "2. Number of albums checked out by a single patron\n" + "3. Most popular actor in the database\n"
                + "4. Most listened to artist in the database\n" + "5. Patron who has checked out the most videos";
        System.out.println(reportMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
        PreparedStatement stmt=null;
    	ResultSet rs=null;
        switch (choice) {
        case 1:
        	//This query comes from [Checkpoint #4]: Find the titles of all tracks by ARTIST released before YEAR.

        	try {
        		stmt= conn.prepareStatement("SELECT DISTINCT Album_Contains.Name FROM Album_Contains, Media_Item WHERE Media_Item.Year<2000 AND Album_Contains.ArtistName = 'Billy Joel'");
        		
        		rs= stmt.executeQuery();
        		
        		while(rs.next()) {
        			
        			System.out.println(rs.getString(1));
        		}
        		
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}finally {
        		if(stmt!=null) {stmt.close();}
        		if(rs!=null) {rs.close();}
        	}
        	break;
        case 2:
        	//[Checkpoint #4]: Find the total number of albums checked out by a single patron (user designates the patron)
        	System.out.println("Patron email to search: ");
        	String patron=keyboard.next();
        	try {
        		stmt= conn.prepareStatement("SELECT COUNT(Name)\r\n"
        				+ "FROM Media_Item\r\n"
        				+ "WHERE Media_Item.Email_Address=? AND Album_Flag=1");
        		
        		stmt.setString(1,patron);
        		rs= stmt.executeQuery();
        		
        		System.out.print("Number of albums "+patron+" checked out is: ");
        		
        		while(rs.next()) {
        			
        			System.out.println(rs.getString(1));
        		}
        		
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}finally {
        		if(stmt!=null) {stmt.close();}
        		if(rs!=null) {rs.close();}
        	}
        	break;
        case 3:
        	//[Checkpoint #5]: Find the most popular actor in the database (i.e. the one who has had the most lent movies)
        	try {
        		stmt= conn.prepareStatement("SELECT Name, COUNT(*)\r\n"
        				+ "FROM Actor_Stars, Checkouts\r\n"
        				+ "WHERE Checkouts.ID=Actor_Stars.ID\r\n"
        				+ "GROUP BY Name\r\n"
        				+ "ORDER BY COUNT(*) DESC\r\n"
        				+ "LIMIT 1");
        		
        	
        		rs= stmt.executeQuery();
        		
        		
        		
        		while(rs.next()) {
        			
        			System.out.println("Most watched actor/actress is "+rs.getString(1));
        		}
        		
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}finally {
        		if(stmt!=null) {stmt.close();}
        		if(rs!=null) {rs.close();}
        	}
        	break;
        case 4:
        	//[Checkpoint #5]: Find the most listened to artist in the database (use the running time of the album and number of times the album has been lent out to calculate)
        	try {
        		stmt= conn.prepareStatement("SELECT DISTINCT Name\r\n"
        				+ "FROM Artist_Authors\r\n"
        				+ "WHERE Artist_Authors.ID=\r\n"
        				+ "(SELECT ID FROM\r\n"
        				+ "(SELECT DISTINCT Media_Item.ID, SUM(length)\r\n"
        				+ "FROM Media_Item, Checkouts\r\n"
        				+ "WHERE Checkouts.ID=Media_Item.ID AND Album_flag=1\r\n"
        				+ "GROUP BY Name\r\n"
        				+ "LIMIT 1))");
        		
        	
        		rs= stmt.executeQuery();
        		
        		
        		
        		while(rs.next()) {
        			
        			System.out.println("Most listened to artist is "+rs.getString(1));
        		}
        		
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}finally {
        		if(stmt!=null) {stmt.close();}
        		if(rs!=null) {rs.close();}
        	}
        	break;
        case 5:
        	//[Checkpoint #4]: Find the patron who has checked out the most videos and the total number of videos they have checked out. 
        	try {
        		stmt= conn.prepareStatement("SELECT Email_Address,COUNT(Name)\r\n"
        				+ "FROM  Media_Item\r\n"
        				+ "WHERE  Movie_Flag=1 AND Email_Address!='NULL'\r\n"
        				+ "Group By Email_Address\r\n"
        				+ "LIMIT 1");
        		
        	
        		rs= stmt.executeQuery();
        		
        		
        		
        		while(rs.next()) {
        			
        			System.out.println("Patron: "+rs.getString(1)+" has checked out the most videos: "+rs.getInt(2));
        		}
        		
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}finally {
        		if(stmt!=null) {stmt.close();}
        		if(rs!=null) {rs.close();}
        	}
        	break;
        default:
        	System.out.println("Invalid Option");
        
        }
    }

    // User selects an artist (provide the name), edit any field of the artist and
    // then save it, updating your internal structure that storage the artist.
    private static void editRecords() {
        final String editMenu = "1. Edit an Artist";
        System.out.println(editMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
        
       
        switch (choice){
        case 1:
			System.out.println("Which artist do you want to edit?");
			String artistString = keyboard.next();
			//TODO: test feature
				
				System.out.println("What would you like to edit about the artist?\n1. Name\n2. Age\n3. Sex");
				int editChoice = keyboard.nextInt();
				switch (editChoice){
					case 1:
						
						
						System.out.println("Enter new name:");
						String name = keyboard.next();
						
						
						break;
					case 2:
						
						System.out.println("Enter new age:");
						int age = keyboard.nextInt();
						
						break;
					case 3:
						
						System.out.println("Enter new sex:");
						String sex = keyboard.next();
						
					
						break;
					default:
						System.out.println("Invalid Option");
				
			}
            break;
        default:
            System.out.println("Invalid Option");
    }
        
        }
        
    

    // The user enter the information to order a new movie, with number of copies
    // purchase, price and an estimated date of arrival
    private static void orderItems() {
        final String orderMenu = "1. Order a Movie\n2. Activate item recieved";
        System.out.println(orderMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
        switch (choice){
        case 1:
			System.out.println("What movie do you want to order?");
			String movieName = keyboard.next();
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
    private static void addNewRecords() throws SQLException{
        final String recordsMenu = "1. Add an artist\n2. Add a Track";
        System.out.println(recordsMenu);
        System.out.println("Enter number option would you like?");
        choice = keyboard.nextInt();
        switch (choice) {

        case 1:
        	PreparedStatement addArtist=null;
        	System.out.println("Name of artist:");
			String artistName = keyboard.next();
			try {
				addArtist=conn.prepareStatement("INSERT INTO Artist VALUES(?,null,null)");
				addArtist.setString(1, artistName);
				int res=addArtist.executeUpdate();
				if(res>0) {
					System.out.println(res+" records added");
				}else {
					System.out.println("Unable to add artist");
				}
				
			}
			catch(SQLException e){
				e.printStackTrace();
			}finally {
				if (addArtist!=null) { addArtist.close();}
			}

            
            break;
        case 2:
        	PreparedStatement addTrack=null;
        	//TODO: Fix reading line in statements
        	System.out.println("Name of track:");
			String trackName = keyboard.next();
			System.out.println("Genre of track:");
			String trackGenre = keyboard.nextLine();
			System.out.println("Name of Artist: ");
			artistName = keyboard.nextLine();
			try {
			addTrack=conn.prepareStatement("INSERT INTO Track VALUES(?,?,?)");
			addTrack.setString(1, trackName);
			addTrack.setString(2, trackGenre);
			addTrack.setString(3, artistName);
			
			int res=addTrack.executeUpdate();
			if(res>0) {
				System.out.println(res+" records added");
			}else {
				System.out.println("Unable to add track");
			}
			}
			catch(SQLException e) {
				e.printStackTrace();

			}
			finally {
				 if(addTrack!=null) { addTrack.close();}
			}
			
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
				
				keyboard.next();
				getArtist = conn.prepareStatement("SELECT * FROM Artist WHERE Name = ?" );
				System.out.println("Enter name of Artist:");
	            String artist=keyboard.nextLine();
	            getArtist.setString(1,artist);

	            artistResult= getArtist.executeQuery();
	            if(!artistResult.next()) {
	            	System.out.println("No artist found");
	            }else {
	            	System.out.println("Name: "+artist
	            			+ "Age: "+artistResult.getInt(2)
	            			+ "Sex: "+artistResult.getString(3));
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
        		String track = keyboard.nextLine();
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