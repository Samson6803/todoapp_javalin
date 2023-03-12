package User;

import io.javalin.security.RouteRole;
public enum Role implements RouteRole {
    GUEST, USER
}
