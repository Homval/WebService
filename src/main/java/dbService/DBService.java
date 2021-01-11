package dbService;

import dbService.userDataSets.UserDataSet;

public interface DBService {
    UserDataSet getUserById(long id) throws DBException;
    long addUser(String name, String password) throws DBException;
    boolean deleteUser(String name, String password) throws DBException;
    UserDataSet getUser(String name) throws DBException;

}
