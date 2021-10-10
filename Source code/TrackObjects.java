public class TrackObjects {
    public String trackName, genre;
    public int length, year;

    // constructors
    public TrackObjects(String setTrackName, String setGenre, int setLength, int setYear) {
        trackName = setTrackName;
        genre = setGenre;
        length = setLength;
        year = setYear;
    }

    // getters
    public String getTrackName() {
        return trackName;
    }

    public String getGenre() {
        return genre;
    }

    public int getLength() {
        return length;
    }

    public int getYear() {
        return year;
    }

    // setters
    public void setTrackname(String setName) {
        trackName = setName;
    }

    public void setGenre(String setGenre) {
        genre = setGenre;
    }

    public void setLength(int setLength) {
        length = setLength;
    }

    public void setYear(int setYear) {
        year = setYear;
    }

    // print Track
    public void printTrackInfo() {
        System.out.println("Name: " + trackName);
        System.out.println("Genre: " + genre);
        System.out.println("Length: " + length);
        System.out.println("Year: " + year);
    }

}