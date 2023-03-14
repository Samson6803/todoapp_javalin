package User;

import Database.Database;
import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class UserController {
    Database database;
    Gson gson;

    public UserController(Database database, Gson gson) {
        this.database = database;
        this.gson = gson;
    }

    private boolean isUserLogged(Context ctx) {
        String isUserLogged = ctx.cookie("isUserLogged");
        return isUserLogged != null && isUserLogged.equals("true");
    }

    public void registerUser(Context ctx) {
        if (!isUserLogged(ctx)) {
            User user = gson.fromJson(ctx.body(), User.class);
            database.addUser(user);
            ctx.status(HttpStatus.OK);
        } else {
            ctx.status(HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    public void logUser(Context ctx) {
        if (!isUserLogged(ctx)) {
            User user = gson.fromJson(ctx.body(), User.class);
            boolean userExists = database.checkUser(user);
            if (userExists) {
                //return full info about user, including id
                user = database.getUser(user.getName());
                ctx.cookie("isUserLogged", "true", 480);
                ctx.cookie("userID", String.valueOf(user.getId()), 480);
                ctx.status(HttpStatus.OK);
            } else {
                ctx.status(HttpStatus.NOT_FOUND);
            }
        } else {
            ctx.status(HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    public void logoutUser(Context ctx) {
        if (isUserLogged(ctx)) {
            ctx.req().getSession().invalidate();
            ctx.removeCookie("userID");
            ctx.cookie("isUserLogged", "false");
        }
    }

    public void userInfo(Context ctx) {
        if (isUserLogged(ctx)) {
            String cookie = ctx.cookie("userID");
            if (cookie != null) {
                User user = database.getUserInfo(Integer.parseInt(cookie));
                System.out.println(gson.toJson(user));
                ctx.json(gson.toJson(user));
                ctx.status(HttpStatus.OK);
            } else {
                ctx.status(HttpStatus.NOT_FOUND);
            }
        } else {
            ctx.status(HttpStatus.UNAUTHORIZED);
        }
    }
}