package accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile user) {
        loginToProfile.put(user.getLogin(), user);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySession(String session) {
        return sessionIdToProfile.get(session);
    }
}
