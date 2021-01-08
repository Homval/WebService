package accounts;


import dbService.DBException;
import dbService.DBService;

public class AccountService {
    private final DBService service;

    public AccountService() {
        service = new DBService();
    }

    public void addNewUser(UserProfile user) {
        try {
            long id = service.addUser(user.getLogin(), user.getPassword());
            System.out.println("New users id: " + id);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public UserProfile getUserByLogin(String login) {
        try {
            return new UserProfile(login, service.getUser(login).getPassword());
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }
}