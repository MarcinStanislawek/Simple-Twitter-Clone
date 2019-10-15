package controllers.servlets;

import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controllers.servlets.utils.ServletUtils.*;

@WebServlet(name = "loginServlet", urlPatterns = {"", "/login"})
public class LoginServlet extends HttpServlet {

    private final String PASSWORD = "password";
    private final String REMEMBER = "remember";
    private final int SECONDS_IN_DAY = 60 * 60 * 24;
    private final String CHECKBOX_SELECTED = "on";
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = new UserDao();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = null;
        String password = null;

        if (null != req.getCookies()) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals(LOGIN_COOKIE)) {
                    login = cookie.getValue();
                    cookie.setMaxAge(SECONDS_IN_DAY);
                    resp.addCookie(cookie);
                } else if (cookie.getName().equals(PASSWORD_COOKIE)) {
                    password = cookie.getValue();
                    cookie.setMaxAge(SECONDS_IN_DAY);
                    resp.addCookie(cookie);
                }
            }
        }

        if (null != login && null != password) {
            req.setAttribute(LOGIN, login);
            req.setAttribute(PASSWORD, password);
            doPost(req, resp);
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        String login = req.getParameter(LOGIN).toUpperCase();
        String password = req.getParameter(PASSWORD);
        String remember = req.getParameter(REMEMBER);

        if (null == login || null == password) {
            login = (String) req.getAttribute(LOGIN);
            password = (String) req.getAttribute(PASSWORD);
        }

        if (userDao.isUserValid(login, password)) {
            req.getSession().setAttribute(LOGIN, login);
            if (null != remember && remember.equals(CHECKBOX_SELECTED)) {
                Cookie loginCookie = new Cookie(LOGIN_COOKIE, login);
                Cookie passwordCookie = new Cookie(PASSWORD_COOKIE, password);
                loginCookie.setMaxAge(SECONDS_IN_DAY);
                passwordCookie.setMaxAge(SECONDS_IN_DAY);
                resp.addCookie(loginCookie);
                resp.addCookie(passwordCookie);
            }
            req.getRequestDispatcher("users").forward(req, resp);
        } else {
            req.setAttribute("hasError", "true");
            req.setAttribute("error", "Login or password incorrect.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
