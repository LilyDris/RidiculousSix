public class Track {
    private String trackName, genre, artist;

    // constructors
    public Track(String setTrackName, String setGenre, String setArtist) {
        trackName = setTrackName;
        genre = setGenre;
        artist = setArtist;
    }

    // getters
    public String getTrackName() {
        return trackName;
    }

    public String getGenre() {
        return genre;
    }

    public String getArtist() {
        return artist;
    }

    // setters
    public void setTrackname(String setName) {
        trackName = setName;
    }

    public void setGenre(String setGenre) {
        genre = setGenre;
    }

    // print Track
    public void printTrackInfo() {
        System.out.println("Name: " + trackName);
        System.out.println("Genre: " + genre);
        System.out.println("Artist: " + artist);
    }

}