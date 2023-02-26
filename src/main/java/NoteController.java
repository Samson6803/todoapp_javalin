import com.google.gson.Gson;
import io.javalin.http.Context;

import java.util.UUID;

public class NoteController {
    Database database = new Database();
    Gson gson = new Gson();
    public void insertNote(Context ctx){
        database.insertNote(gson.fromJson(ctx.body(), Note.class));
        ctx.status(200);
    }

    public void getNotes(Context ctx){
        ctx.json(gson.toJson(database.getNotes(ctx.queryParam("id"))));
        ctx.status(200);
    }

    public void deleteNote(Context ctx){
        database.deleteNote(ctx.queryParam("id"));
        ctx.status(200);
    }
}
