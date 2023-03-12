package User;

import org.eclipse.jetty.server.session.*;

public class SessionUtil {
    private static String url = "jdbc:postgresql://localhost:5432/jetty?user=postgres&password=postgresql";
    private static String driver = "org.postgresql.Driver";
    public static SessionHandler sqlSessionHandler(){
        SessionHandler sessionHandler = new SessionHandler();
        SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
        sessionCache.setSessionDataStore(
            jdbcSessionDataStoreFactory().getSessionDataStore(sessionHandler)
        );
        sessionHandler.setSessionCache(sessionCache);
        sessionHandler.setHttpOnly(true);
        sessionHandler.setMaxInactiveInterval(480);
        return sessionHandler;
    }

    public static JDBCSessionDataStoreFactory jdbcSessionDataStoreFactory(){
        DatabaseAdaptor databaseAdaptor = new DatabaseAdaptor();
        databaseAdaptor.setDriverInfo(driver, url);
        JDBCSessionDataStoreFactory jdbcSessionDataStoreFactory = new JDBCSessionDataStoreFactory();
        jdbcSessionDataStoreFactory.setDatabaseAdaptor(databaseAdaptor);
        return jdbcSessionDataStoreFactory;
    }




}
