public class SQLParser {
    public String insertNoteQuery(){
        return "INSERT INTO note(name, description, author) VALUES (?,?,?);";
    }
    public String getNotesQuery(){
        return "SELECT * FROM note";
    }
    public String getNoteQuery(){
        return "SELECT * FROM note WHERE id = ?";
    }
    public String deleteNoteQuery(){
        return "DELETE FROM note WHERE id=?";
    }
    public String updateNoteQuery(){
        return "UPDATE note SET " +
                "name = ?," +
                "description = ?," +
                "author = ? WHERE id = ?";
    }
}