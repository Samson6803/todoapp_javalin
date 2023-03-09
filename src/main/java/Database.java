import at.favre.lib.crypto.bcrypt.BCrypt;

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

    public Note getNotes(String id){
        String query = sqlParser.getNoteQuery();
        ResultSet resultSet = null;
        Note note = null;
        try(Connection connection = connectToDB()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,Integer.parseInt(id));
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                note = getNote(resultSet);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return note;
    }

    public List<Note> getNotes(){
        String query = sqlParser.getNotesQuery();
        ResultSet resultSet = null;
        List<Note> notes = new ArrayList<>();
        try(Connection connection = connectToDB()){
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                notes.add(getNote(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return notes;
    }

    public void insertNote(Note note) {
        String query = sqlParser.insertNoteQuery();
        try(Connection connection = connectToDB()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, note.getName());
            statement.setString(2, note.getDescription());
            statement.setString(3, note.getAuthor());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteNote(String id){
        String query = sqlParser.deleteNoteQuery();
        try(Connection connection = connectToDB()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,Integer.parseInt(id));
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateNote(Note note, String id){
        String query = sqlParser.updateNoteQuery();
        try(Connection connection = connectToDB()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, note.getName());
            statement.setString(2, note.getDescription());
            statement.setString(3, note.getAuthor());
            statement.setInt(4, Integer.parseInt(id));
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addUser(User user){
        String hashedPassword = BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, user.getPassword().toCharArray());
        String query = sqlParser.addUser();
        try(Connection connection = connectToDB()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, hashedPassword);
            statement.setString(3, user.getEmail());
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Boolean checkUser(User user){
        String query = sqlParser.getUser();
        ResultSet rs = null;
        String hashedPassword = null;
        BCrypt.Result result = null;
        try(Connection connection = connectToDB()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            rs = statement.executeQuery();

            if(rs.next()){
                hashedPassword = rs.getString("hashedPassword");
                result = BCrypt.verifyer().verify(user.getPassword().toCharArray(), hashedPassword);
                return result.verified;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}