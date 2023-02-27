import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;



public class Main {
    public static void main(String[] args){
        NoteController controller = new NoteController();

        Javalin app = Javalin.create(javalinConfig -> javalinConfig.staticFiles.add("/public"))
                .start();
        app.routes(()-> {
           path("notes", ()->{
                post(controller::insertNote);
                get(controller::getNotes);
                delete(controller::deleteNote);
                put(controller::updateNote);
           });
        });




    }
}