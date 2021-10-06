import java.util.List;

public class ArtistOjects {
    public String name;
    public int age;
    public List<String> genres;
    public List<TrackObject> tracks;

    // constructor
    public ArtistOjects(String name, int age, List<String> genres, List<TrackObject> tracks) {
        this.name = name;
        this.age = age;
        this.genres = genres;
        this.tracks = tracks;
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

    public List<TrackObject> getTracks() {
        return tracks;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setTracks(List<TrackObject> tracks) {
        this.tracks = tracks;
    }

    // adders
    public void addGenre(String genre) {
        this.genres.add(genre);
    }

    public void addTrack(TrackObject track) {
        this.tracks.add(track);
    }

    //print Artist
    public void printArtistInfo(){
        System.out.println("Artist: "+name);
        System.out.println("Age: "+age);
        System.out.println("Genres: "+genres);
        System.out.println("Tracks: "+tracks);
    }
}
