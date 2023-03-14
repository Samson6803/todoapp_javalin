import Database.Database;
import Note.NoteController;
import User.SessionUtil;
import User.UserController;
import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) {
        Database database = new Database("postgres", "postgresql", "jdbc:postgresql://localhost:5432/data");
        Gson gson = new Gson();

        NoteController noteController = new NoteController(database, gson);
        UserController userController = new UserController(database, gson);

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.jetty.sessionHandler(SessionUtil::sqlSessionHandler);
            javalinConfig.staticFiles.add("/public", Location.CLASSPATH);
        }).start();

        app.before(ctx -> {
            String isUserLogged = ctx.sessionAttribute("isUserLogged");
            if (isUserLogged == null) {
                ctx.sessionAttribute("isUserLogged", "false");
            }
        });

        app.routes(() -> {
            path("api/", () -> {
                path("notes/{noteID}", () -> {
                    crud(noteController);
                });
                path("user/", () -> {
                    path("login", () -> {
                        post(userController::logUser);
                    });

                    path("register", () -> {
                        post(userController::registerUser);
                    });

                    path("logout", () -> {
                        get(userController::logoutUser);
                    });
                    path("me", () -> {
                        get(userController::userInfo);
                    });
                });

            });
        });
    }
}