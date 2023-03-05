import at.favre.lib.crypto.bcrypt.BCrypt;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;
public class Main {
    public static void main(String[] args){
        NoteController noteController = new NoteController();

        Javalin app = Javalin.create(javalinConfig -> javalinConfig.staticFiles.add("/public"))
                .start();
        app.routes(()-> {
           path("notes/{noteId}", ()->{
                crud(noteController);
           });
           path("login", ()->{

           });
           path("register", ()->{

           });
        });



    }
}