package dbService;

import dbService.dao.UserDao;
import dbService.userDataSets.UserDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.function.Function;

public class DBService {
    private static final String hibernate_show_sql = "false";
    private static final String hibernate_hbm2ddl_auto = "update";

    private final SessionFactory sessionFactory;

    public DBService() {
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "Homvalmysql1");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);

        return configuration;
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public UserDataSet getUserById(long id) throws DBException {
        return nonTransactionSession(session -> {
            UserDao userDao = new UserDao(session);
            return userDao.getUserById(id);
        });
    }

    public long addUser(String name, String password) throws DBException {
        return transactionSession(session -> {
            UserDao userDao = new UserDao(session);
            return userDao.addNewUser(name, password);
        });
    }

    public boolean deleteUser(String name, String password) throws DBException {
        return transactionSession(session -> {
            UserDao userDao = new UserDao(session);
            userDao.deleteUser(name, password);
            return true;
        });
    }

    public UserDataSet getUser(String name) throws DBException {
        return nonTransactionSession(session -> {
            UserDao userDao = new UserDao(session);
            return userDao.getUserIdByName(name);
        });
    }

    public <R> R transactionSession(Function<Session, R> func) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            R result = func.apply(session);
            transaction.commit();
            session.close();
            return result;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public <R> R nonTransactionSession(Function<Session, R> func) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            R result = func.apply(session);
            session.close();
            return result;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }


}
