package Note;

public class Note {
    private String title;
    private String description;
    private int id;

    public Note(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getID() {return id;}

}