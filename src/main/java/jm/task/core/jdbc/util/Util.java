package jm.task.core.jdbc.util;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;



public class Util {
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/newschema";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "52532137";
    public static SessionFactory getConnectionHibernate() {
        Configuration configuration = new Configuration();
        SessionFactory factory;
        //.configure("jdbc:mysql://127.0.0.1:3306/newschema")
        //.addAnnotatedClass(User.class)
        //.buildSessionFactory();
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, DB_DRIVER);
        settings.put(Environment.URL, DB_URL);//"jdbc:mysql://localhost:3306/java_dmeo?useSSL=false");
        settings.put(Environment.USER, DB_USERNAME);
        settings.put(Environment.PASS, DB_PASSWORD);
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "");
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);

    }
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME,DB_PASSWORD);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
