import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private String user = "postgres";
    private String password = "postgresql";
    private String url = "jdbc:postgresql://localhost:5432/data";
    private SQLParser sqlParser = new SQLParser();

    private Connection connectToDB(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    private Note getNote(ResultSet resultSet){
        Note note = null;
        try{
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String author = resultSet.getString("author");
            int ID = resultSet.getInt("id");
            note = new Note(name, description, author, ID);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return note;
    }
    public void insertNote(Note note) {
        Connection connection = connectToDB();
        String query = sqlParser.insertNoteQuery(note.getName(), note.getDescription(), note.getAuthor());
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Note> getNotes(String id){
        Connection connection = connectToDB();
        String query = id == null ? sqlParser.getNotesQuery() : sqlParser.getNotesQuery(id);
        ResultSet resultSet = null;
        List<Note> notes = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                Note note = getNote(resultSet);
                notes.add(note);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return notes;
    }

    public void deleteNote(String id){
        Connection connection = connectToDB();
        String query = sqlParser.deleteNoteQuery(id);
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


}