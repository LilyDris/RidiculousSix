import java.util.List;

public class Artist {
    private String name;
    private List<String> genres;
    private List<Track> tracks;

    // constructor with name, age, and genres
    public Artist(String setName, List<String> setGenres, List<Track> setTracks) {
        name = setName;
        genres = setGenres;
        tracks = setTracks;
    }

    // getters
    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    // setters
    public void setName(String newName) {
        name = newName;
    }

    public void setGenres(List<String> setGenres) {
        genres = setGenres;
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public void printArtistTracks() {
        for (int i = 0; i < tracks.size(); i++) {
            System.out.println(tracks.get(i).getTrackName());
        }
    }

    // print Artist
    public void printArtistInfo() {
        System.out.println("Artist: " + name);
        System.out.println("Genres: " + genres);
        System.out.print("Tracks: ");
        printArtistTracks();
    }
}