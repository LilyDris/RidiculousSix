import java.util.ArrayList;
import java.util.List;


public class ArtistOjects {
    public String name;
    public int age;
    public List<String> genres;

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
    
}
