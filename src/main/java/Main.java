import Database.Database;
import Note.NoteController;
import User.SessionUtil;
import User.UserController;
import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import static io.javalin.apibuilder.ApiBuilder.*;
public class Main {
    public static void main(String[] args){
        Database database = new Database("postgres", "postgresql", "jdbc:postgresql://localhost:5432/data");
        Gson gson = new Gson();

        NoteController noteController = new NoteController(database, gson);
        UserController userController = new UserController(database, gson);

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.staticFiles.add("/public", Location.CLASSPATH);
            javalinConfig.jetty.sessionHandler(SessionUtil::sqlSessionHandler);
        }).start();

        app.before(ctx -> {
            Integer userID = ctx.sessionAttribute("userID");
            if (userID == null){
                // userID 0 means guest
                ctx.sessionAttribute("userID", 0);
                userController.setGuestRole(ctx);
            }
        });

        app.routes(()-> {
           path("api/", ()->{
               path("notes/{noteID}", ()->{
                   crud(noteController);
               });

               path("login", ()->{
                   post(userController::logUser);
               });

               path("register", ()->{
                   post(userController::addUser);
               });

               path("logout", ()->{
                   get(userController::logoutUser);
               });
           });
        });
    }
}