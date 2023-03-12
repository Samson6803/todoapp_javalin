package User;
import Database.Database;
import com.google.gson.Gson;
import io.javalin.http.Context;

public class UserController {
    Database database;
    Gson gson;

    public UserController(Database database, Gson gson){
        this.database = database;
        this.gson = gson;
    }

    public void addUser(Context ctx){
        User user = gson.fromJson(ctx.body(), User.class);
        database.addUser(user);
    }

    public void logUser(Context ctx){
        User user = gson.fromJson(ctx.body(), User.class);
        boolean userExists = database.checkUser(user);
        if (userExists){
            //return full info about user, including id
            user = database.getUser(user.getName());
            ctx.req().changeSessionId();
            ctx.sessionAttribute("userID", user.getId());
        }
        ctx.redirect("notes.html");
    }

    public void logoutUser(Context ctx){
        Integer userID = ctx.sessionAttribute("userID");
        //there's a session and it isn't a guest session
        if (userID != null && userID != 0){
            ctx.req().getSession().invalidate();
            ctx.redirect("notes");
        }
    }
}