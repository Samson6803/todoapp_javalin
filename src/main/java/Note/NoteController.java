package Note;

import Database.Database;
import com.google.gson.Gson;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class NoteController implements CrudHandler {
    Database database;
    Gson gson;

    public NoteController(Database database, Gson gson) {
        this.database = database;
        this.gson = gson;
    }

    @Override
    public void create(@NotNull Context ctx) {
        String userID = ctx.cookie("userID");
        if (userID != null) {
            database.insertNote(gson.fromJson(ctx.body(), Note.class), Integer.parseInt(userID));
        }
    }

    @Override
    public void delete(@NotNull Context ctx, @NotNull String userId) {
        database.deleteNote(userId);
    }

    @Override
    public void getAll(@NotNull Context ctx) {
        String userID = ctx.cookie("userID");
        if(userID != null) {
            ctx.json(gson.toJson(database.getNotes(userID)));
        }
    }

    @Override
    public void getOne(@NotNull Context ctx, @NotNull String noteId) {
        String userID = ctx.cookie("userID");
        ctx.json(gson.toJson(database.getNotes(userID, noteId)));
    }

    @Override
    public void update(@NotNull Context ctx, @NotNull String noteId) {
        database.updateNote(gson.fromJson(ctx.body(), Note.class), noteId);
    }
}
