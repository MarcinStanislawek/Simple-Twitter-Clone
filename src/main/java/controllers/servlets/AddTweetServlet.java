package controllers.servlets;

import controllers.servlets.utils.ServletUtils;
import dao.TweetDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddTweetServlet", value = "/addTweet")
public class AddTweetServlet extends HttpServlet {
    private TweetDao tweetDao;

    @Override
    public void init() throws ServletException {
        tweetDao = new TweetDao();
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF8");
        String currentUserLogin = ServletUtils.getUserLoginFromSession(req);
        String tweetMessage = req.getParameter("tweetMessage");
        tweetDao.addTweet(currentUserLogin, tweetMessage);
        req.getRequestDispatcher("/messages").forward(req, resp);
    }
}
