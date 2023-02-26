import java.util.UUID;

public class Note {
    private String name;
    private String description;
    private String author;
    private int ID;

    public Note(String name, String description, String author, int ID) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public int getID() {return ID;}

}