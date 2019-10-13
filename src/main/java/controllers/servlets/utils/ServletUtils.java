package controllers.servlets.utils;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    public static final String LOGIN = "login";
    public static final String LOGIN_COOKIE = "twitter_login";
    public static final String PASSWORD_COOKIE = "twitter_password";

    public static String getUserLoginFromSession(HttpServletRequest req) {
        return (String) req.getSession().getAttribute(LOGIN);
    }
}
