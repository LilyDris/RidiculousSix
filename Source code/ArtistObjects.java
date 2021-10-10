import java.util.List;
import java.util.Scanner;

public class ArtistObjects {
    String name;
    int age;
    List<String> genres;
    List<TrackObjects> tracks;
    Scanner keyboard= new Scanner(System.in);

    // constructor with name, age, and genres
    public ArtistObjects(String setName, int setAge, List<String> setGenres, List<TrackObjects> setTracks) {
        name = setName;
        age = setAge;
        genres = setGenres;
        tracks = setTracks;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getGenres() {
        return genres;
    }
	public List<TrackObjects> getTracks(){
			return tracks;
	}
    // setters
    public void setName(String newName) {
        name = newName;
    }

    public void setAge(int setAge) {
        age = setAge;
    }

    public void setGenres(List<String> setGenres) {
        genres = setGenres;
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }
	
	//print Artist
    public void printArtistInfo(){
        System.out.println("Artist: "+name);
        System.out.println("Age: "+age);
        System.out.println("Genres: "+genres);
        System.out.println("Tracks: "+tracks);
    }
}