import java.time.LocalDate;

public class SQLParser {
    public String insertNoteQuery(String name, String description, String author){
        return String.format("INSERT INTO note(name, description, author) VALUES ('%s','%s','%s');",
                name, description, author);
    }
    public String getNotesQuery(){
        return "SELECT * FROM note";
    }
    public String getNotesQuery(String id){
        return String.format("SELECT * FROM note WHERE id = '%s'", id);
    }
    public String deleteNoteQuery(String id){
        return String.format("DELETE FROM note WHERE id='%s'", id);
    }
    public String updateNoteQuery(String name, String description, String author, int id){
        String query = String.format("UPDATE note SET " +
                "name = '%s'," +
                "description = '%s'," +
                "author = '%s' WHERE id = %d",name, description, author, id);
        System.out.println(query);
        return query;

    }

}
