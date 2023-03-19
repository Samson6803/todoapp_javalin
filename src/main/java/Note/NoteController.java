package Note;

import Database.Database;
import com.google.gson.Gson;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

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
    public void delete(@NotNull Context ctx, @NotNull String s) {
        database.deleteNote(s);
    }

    //TODO: getall should return only logged users notes
    @Override
    public void getAll(@NotNull Context ctx) {
        ctx.json(gson.toJson(database.getNotes()));
    }

    @Override
    public void getOne(@NotNull Context ctx, @NotNull String s) {
        ctx.json(gson.toJson(database.getNotes(s)));
    }

    @Override
    public void update(@NotNull Context ctx, @NotNull String s) {
        database.updateNote(gson.fromJson(ctx.body(), Note.class), s);
    }
}
