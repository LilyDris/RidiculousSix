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
            choice = Integer.parseInt(keyboard.nextLine());
            
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
        choice = Integer.parseInt(keyboard.nextLine());
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
        	String patron=keyboard.nextLine();
        	try {
        		stmt= conn.prepareStatement("SELECT COUNT(DISTINCT Media_Item.Name)\r\n"
        				+ "FROM Checkouts, Media_Item\r\n"
        				+ "Where Email_Address=? AND Album_Flag=1");
        		
        		stmt.setString(1,patron);
        		rs= stmt.executeQuery();
        		
        		System.out.print("Number of albums "+patron+" checked out is: ");
        		
        		while(rs.next()) {
        			
        			System.out.println(rs.getInt(1));
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
    private static void editRecords() throws SQLException {
        final String editMenu = "1. Edit an Artist";
        System.out.println(editMenu);
        System.out.println("Enter number option would you like?");
        choice = Integer.parseInt(keyboard.nextLine());
        
       
        switch (choice){
        case 1:
			System.out.println("Which artist do you want to edit?");
			String artistString = keyboard.nextLine();
			
				
				System.out.println("What would you like to edit about the artist?\n1. Name\n2. Age\n3. Sex");
				int editChoice = Integer.parseInt(keyboard.nextLine());
				switch (editChoice){
					case 1:
						System.out.println("Enter new name:");
						String name = keyboard.nextLine();
								PreparedStatement stmt=null;
								int rs=0;
						try {
			        		stmt= conn.prepareStatement("UPDATE Artist SET Name=? WHERE Name= ?");
			        		stmt.setString(1,name);
			        		stmt.setString(2, artistString);
			        		rs= stmt.executeUpdate();     		
			        		if(rs>0) {
			        			System.out.println(rs+" record(s) edited");
							}else {
								System.out.println("Unable to edit artist");
							}
			        		
			        	}catch(SQLException e) {
			        		e.printStackTrace();
			        	}finally {
			        		if(stmt!=null) {stmt.close();}
			        		
			        	}
						break;
					case 2:
						stmt=null;
						rs=0;
						System.out.println("Enter new age:");
						int age = Integer.parseInt(keyboard.nextLine());
						
				try {
	        		stmt= conn.prepareStatement("UPDATE Artist SET Age=? WHERE Name= ?");
	        		stmt.setInt(1,age);
	        		stmt.setString(2, artistString);
	        		rs= stmt.executeUpdate();     		
	        		if(rs>0) {
	        			System.out.println(rs+" record(s) edited");
					}else {
						System.out.println("Unable to edit artist");
					}
	        		
	        	}catch(SQLException e) {
	        		e.printStackTrace();
	        	}finally {
	        		if(stmt!=null) {stmt.close();}
	        		
	        	}
						
						break;
					case 3:
						
						System.out.println("Enter new sex:");
						String sex = keyboard.nextLine();
						stmt=null;
						rs=0;
				try {
	        		stmt= conn.prepareStatement("UPDATE Artist SET Sex=? WHERE Name= ?");
	        		stmt.setString(1,sex);
	        		stmt.setString(2, artistString);
	        		rs= stmt.executeUpdate();     		
	        		if(rs>0) {
	        			System.out.println(rs+" record(s) edited");
					}else {
						System.out.println("Unable to edit artist");
					}
	        		
	        	}catch(SQLException e) {
	        		e.printStackTrace();
	        	}finally {
	        		if(stmt!=null) {stmt.close();}
	        		
	        	}
					
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
    private static void orderItems() throws SQLException {
        final String orderMenu = "1. Order a Movie\n2. Activate item recieved";
        System.out.println(orderMenu);
        System.out.println("Enter number option would you like?");
        choice = Integer.parseInt(keyboard.nextLine());
        switch (choice){
        case 1:
			System.out.println("What movie do you want to order?");
			String movie = keyboard.nextLine();
			System.out.println("Order number: ");
			int orderNum=keyboard.nextInt(); keyboard.nextLine();
			System.out.println("How many copies? ");
			int copies=keyboard.nextInt(); keyboard.nextLine();
			System.out.println("Price of order in the format of \'$50\': ");
			String price=keyboard.nextLine();
			System.out.println("Shipping city: ");
			String city=keyboard.nextLine();
			System.out.println("Shipping state: ");
			String state= keyboard.nextLine();
			
			
			PreparedStatement stmt=null;
			int rs=0;
			try {
        		stmt= conn.prepareStatement("INSERT INTO Orders VALUES (?,?,?,null,?,?)");
        		stmt.setInt(1,orderNum);
        		stmt.setString(2, price);
        		stmt.setInt(3, copies);
        		stmt.setString(4,city);
        		stmt.setString(5, state);
        		rs= stmt.executeUpdate();     		
        		if(rs>0) {
        			System.out.println(rs+" record(s) edited");
				}else {
					System.out.println("Unable to edit artist");
				}
        		
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}finally {
        		if(stmt!=null) {stmt.close();}
        		
        	}
            break;
        case 2:
        	PreparedStatement stmt2=null;
        	ResultSet res=null;
        	stmt=null;
        	
			System.out.println("Order number: ");
			orderNum=keyboard.nextInt(); keyboard.nextLine();
			System.out.println("Media ID for Movie: ");
			int id=Integer.parseInt(keyboard.nextLine());
			
        	try {
        		stmt2=conn.prepareStatement( "SELECT City,State FROM Orders WHERE Orders_Number=?");
        		stmt= conn.prepareStatement("INSERT INTO Media_Item VALUES (?,1,null,null,null,0,0,null,1,null,0,null,null,1,?,?,?)");
        		stmt2.setInt(1, orderNum);
        		res=stmt2.executeQuery();
        		stmt.setInt(1,id);
        		stmt.setInt(2,orderNum);
        		if(res.next()) {
        			stmt.setString(3,res.getString(1));
        			stmt.setString(4,res.getString(2));
        		}
        		rs= stmt.executeUpdate();     		
        		if(rs>0) {
        			System.out.println(rs+" record(s) edited");
        			stmt=conn.prepareStatement("DELETE FROM Orders WHERE Orders_Number=?");
        			stmt.setInt(1, id);
        			stmt.executeUpdate();
				}else {
					System.out.println("Unable to edit artist");
				}
        		
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}finally {
        		if(stmt!=null) {stmt.close();}
        		
        	}
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
        choice = Integer.parseInt(keyboard.nextLine());
        switch (choice) {

        case 1:
        	PreparedStatement addArtist=null;
        	System.out.println("Name of artist:");
			String artistName = keyboard.nextLine();
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
        	
        	System.out.println("Name of track:");
			String trackName = keyboard.nextLine();
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
        choice = Integer.parseInt(keyboard.nextLine());

      
        switch (choice) {

        case 1:
        	PreparedStatement getArtist=null;
        	ResultSet artistResult= null;
			try {
				
				
				getArtist = conn.prepareStatement("SELECT * FROM Artist WHERE Name = ?" );
				System.out.println("Enter name of Artist:");
	            String artist=keyboard.nextLine();
	            getArtist.setString(1,artist);

	            artistResult= getArtist.executeQuery();
	            if(!artistResult.next()) {
	            	System.out.println("No artist found");
	            }else {
	            	System.out.println("Name: "+artist
	            			+ " Age: "+artistResult.getInt(3)
	            			+ " Sex: "+artistResult.getString(2));
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
        	
        	PreparedStatement getTrack=null;
        	ResultSet trackResult=null;
        	try {
        		getTrack=conn.prepareStatement("SELECT * FROM Track WHERE Name = ?" );
        		System.out.println("Enter name of Track:");
        		String track = keyboard.nextLine();
        		getTrack.setString(1, track);
        		trackResult=getTrack.executeQuery();
        		

        		if(trackResult.next()){  
	            	String name=trackResult.getString(1);
	            	String genre=trackResult.getString(2);
	            	String artistName=trackResult.getString(3);
	            	System.out.println("Name: "+name+", Genre: "+ genre+", Artist Name: "+artistName);
	            	} else {
	            		System.out.println("No track found");
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
