package Database;

import Note.Note;
import User.User;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Database {
    private final String user;
    private final String password;
    private final String url;

    public Database(String user, String password, String url) {
        this.user = user;
        this.password = password;
        this.url = url;
    }

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
            String name = resultSet.getString("title");
            String description = resultSet.getString("description");
            int ID = resultSet.getInt("id");
            note = new Note(name, description, ID);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return note;
    }

    public Note getNotes(String id){
        String query = SQLParser.getNoteQuery();
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
        String query = SQLParser.getNotesQuery();
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

    public void insertNote(Note note, int userID) {
        String query = SQLParser.insertNoteQuery();
        try(Connection connection = connectToDB()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, note.getName());
            statement.setString(2, note.getDescription());
            statement.setInt(3, userID);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteNote(String id){
        String query = SQLParser.deleteNoteQuery();
        try(Connection connection = connectToDB()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,Integer.parseInt(id));
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateNote(Note note, String id){
        String query = SQLParser.updateNoteQuery();
        try(Connection connection = connectToDB()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, note.getName());
            statement.setString(2, note.getDescription());
            statement.setInt(3, Integer.parseInt(id));
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addUser(User user){
        String hashedPassword = BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, user.getPassword().toCharArray());
        String query = SQLParser.addUser();
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

    public boolean checkUser(User user){
        String query = SQLParser.getUser();
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

    public User getUser(String name){
        String query = SQLParser.getUser();
        User user = null;
        try(Connection connection = connectToDB()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                int userId = rs.getInt("id");
                String userName = rs.getString("name");
                String userEmail = rs.getString("email");
                String hashedPassword = rs.getString("hashedPassword");
                user = new User(userName, hashedPassword, userEmail, userId);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }
}