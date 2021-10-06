public class TrackObject {
    public String trackName, genre;
    public int length, year;

    // constructors
    public TrackObject(String trackName, String genre, int length, int year) {
        this.trackName = trackName;
        this.genre = genre;
        this.length = length;
        this.year = year;
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
    public void setTrackname(String name) {
        this.trackName = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //print Track
    public void printTrackInfo(){
        System.out.println("Name: "+trackName);
        System.out.println("Genre: "+genre);
        System.out.println("Length: "+length);
        System.out.println("Year: "+year);
    }

}
