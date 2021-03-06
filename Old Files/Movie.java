import java.util.List;

public class Movie {
    private String name, director;
    private int length, year, price = 5;
    private double rating;
    private List<String> genres;

    // constructor
    public Movie(String setName, String setDirector, int setLength, int setYear, double setRating,
            List<String> setGenres) {
        name = setName;
        director = setDirector;
        length = setLength;
        year = setYear;
        rating = setRating;
        genres = setGenres;
    }

    // getters
    public String getName() {
        return name;
    }

    public String getDirector() {
        return director;
    }

    public int getLength() {
        return length;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getPrice() {
        return price;
    }

    // setters
    public void setName(String setName) {
        name = setName;
    }

    public void setDirector(String setDirector) {
        director = setDirector;
    }

    public void setLength(int setLength) {
        length = setLength;
    }

    public void setYear(int setYear) {
        year = setYear;
    }

    public void setRating(double setRating) {
        rating = setRating;
    }

    public void setGenres(List<String> setGenres) {
        genres = setGenres;
    }

    public void setPrice(int newPrice) {
        price = newPrice;
    }

    // adders
    public void addGenre(String genre) {
        genres.add(genre);
    }
}