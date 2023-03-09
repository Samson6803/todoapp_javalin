import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.security.RouteRole;
import org.eclipse.jetty.server.Handler;

import java.util.Objects;

import static io.javalin.apibuilder.ApiBuilder.*;
public class Main {
    public static void main(String[] args){
        NoteController noteController = new NoteController();
        UserController userController = new UserController();

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.staticFiles.add("/public");
            javalinConfig.jetty.sessionHandler(SessionUtil::sqlSessionHandler);
        }).start();

        app.before(ctx -> {
            if(ctx.sessionAttribute("user") == null){
                ctx.sessionAttribute("user", "guest");
            }

            if (ctx.sessionAttribute("user") == "guest"){
                System.out.println("jesteś guestem");
            } else {
                System.out.println(ctx.sessionAttribute("user").toString());
            }
        });

        app.routes(()-> {
           path("notes/{noteId}", ()->{
                crud(noteController);
           });
           path("user", () ->{
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

    public static void accessManager(Handler handler, Context ctx, RouteRole Role){

    }
}