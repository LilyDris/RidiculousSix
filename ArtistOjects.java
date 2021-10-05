import java.util.ArrayList;
import java.util.List;


public class ArtistOjects {
    public String name;
    public int age;
    public List<String> genres;
    public List<TrackObject> tracks;

    //constructor with name   
    public ArtistOjects(String name){
        this.name=name;
        genres= new ArrayList<>();
    }

    //constructor with name and age
    public ArtistOjects(String name, int age){
        this.name=name;
        this.age=age;
        genres=new ArrayList<>();
    }

    //constructor with name, age, and genres
    public ArtistOjects(String name, int age, List<String> genres){
        this.name=name;
        this.age=age;
        this.genres=genres;
    }

    //getters
    public String getName(){return name;}
    public int getAge(){return age;}
    public List<String> getGenres(){return genres;}

    //setters
    public void setName(String name){this.name=name;}
    public void setAge(int age){this.age = age;}
    public void setGenres(List<String> genres){this.genres=genres;}

    public void addGenre(String genre){ this.genres.add(genre);}
}
