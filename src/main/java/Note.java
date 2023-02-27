import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Note {
    private String name;
    private String description;
    private String author;
    private int id;

    public Note(String name, String description, String author, int id) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.id = id;
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

    public int getID() {return id;}

}