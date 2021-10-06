import java.util.ArrayList;
import java.util.List;

public class MovieObjects {
    public String name, director;
    public int length, year;
    public double rating;
    List<String> genres;

    //constructor
    public MovieObjects(String name, String director, int length, int year, double rating, List<String> genres){
        this.name=name;
        this.director=director;
        this.length=length;
        this.year=year;
        this.rating=rating;
        this.genres=genres;
    }

    //getters
    public String getName(){return name;}
    public String getDirector(){return director;}
    public int getLength(){return length;}
    public int getYear(){return year;}
    public double getRating(){return rating;}
    public List<String> getGenres(){return genres;}
    
    //setters
    public void setName(String name){this.name=name;}
    public void setDirector(String director){this.director=director;}
    public void setLength(int length){this.length=length;}
    public void setYear(int year){this.year=year;}
    public void setRating(double rating){this.rating=rating;}
    public void setGenres(List<String> genres){this.genres=genres;}

    //adders
    public void addGenre(String genre){this.genres.add(genre);}
}
