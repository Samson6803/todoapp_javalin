package Database;

public class SQLParser {
    public static String insertNoteQuery() {
        return "INSERT INTO note(title, description, users_id) VALUES (?,?,?);";
    }

    public static String getNotesQuery() {
        return "SELECT * FROM note";
    }

    public static String getNoteQuery() {
        return "SELECT * FROM note WHERE id = ?";
    }

    public static String deleteNoteQuery() {
        return "DELETE FROM note WHERE id=?";
    }

    public static String updateNoteQuery() {
        return "UPDATE note SET " +
                "title = ?," +
                "description = ? " +
                "WHERE id = ?";
    }

    public static String addUser() {
        return "INSERT INTO users(name, hashedPassword, email) VALUES(? ,? ,?);";
    }

    public static String getUserByName() {
        return "SELECT * FROM users WHERE name = ?;";
    }

    public static String getUserByID() {
        return "SELECT * FROM users WHERE id = ?;";
    }
}