package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("pass");

        UserProfile userProfile = accountService.getUserByLogin(login);

        resp.setContentType("text/html;charset=utf-8");

        if (userProfile == null || accountService.getUserByLogin(login) == null || !userProfile.getPassword().equals(password)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().println("Unauthorized");
            return;
        }

        String sessionId = req.getSession().getId();
        accountService.addSession(sessionId,userProfile);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("Authorized: " + login);
    }
}
