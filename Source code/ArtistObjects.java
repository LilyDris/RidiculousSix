import java.util.List;

public class ArtistObjects {
    String name;
    int age;
    List<String> genres;
    List<TrackObject> tracks;

    // constructor with name, age, and genres
    public ArtistObjects(String setName, int setAge, List<String> setGenres, List<TrackObject> setTracks) {
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
}
