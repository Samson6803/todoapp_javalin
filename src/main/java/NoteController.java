import com.google.gson.Gson;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class NoteController implements CrudHandler {
    Database database = new Database();
    Gson gson = new Gson();

    @Override
    public void create(@NotNull Context ctx) {
        database.insertNote(gson.fromJson(ctx.body(), Note.class));
        ctx.status(200);
    }

    @Override
    public void delete(@NotNull Context ctx, @NotNull String s) {
        database.deleteNote(s);
        ctx.status(200);
    }

    @Override
    public void getAll(@NotNull Context ctx) {
        ctx.json(gson.toJson(database.getNotes()));
        ctx.status(200);
    }

    @Override
    public void getOne(@NotNull Context ctx, @NotNull String s) {
        ctx.json(gson.toJson(database.getNotes(s)));
        ctx.status(200);
    }

    @Override
    public void update(@NotNull Context ctx, @NotNull String s) {
        database.updateNote(gson.fromJson(ctx.body(), Note.class), s);
        ctx.status(200);
    }
}
