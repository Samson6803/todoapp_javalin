import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.gson.Gson;
import io.javalin.http.Context;

public class UserController {
    Database database = new Database();
    Gson gson = new Gson();

    public void addUser(Context ctx){
        User user = gson.fromJson(ctx.body(), User.class);
        database.addUser(user);
    }

    public void logUser(Context ctx){
        User user = gson.fromJson(ctx.body(), User.class);
        Boolean userExists = database.checkUser(user);
        if (userExists){
            ctx.req().changeSessionId();
            ctx.sessionAttribute("user", user.getName());
        } else {
            System.out.println("Nie ma takiego usera");
        }
    }

    public void logoutUser(Context ctx){
        String sessionUser = ctx.sessionAttribute("user");
        if (sessionUser != null && !sessionUser.equals("guest")){
            ctx.req().getSession().invalidate();
        }
    }
}
