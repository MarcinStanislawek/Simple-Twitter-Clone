package controllers.servlets;

import controllers.servlets.utils.ServletUtils;
import dao.TweetDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteTweetServlet", value = "/deleteTweet")
public class DeleteTweetServlet extends HttpServlet {
    private TweetDao tweetDao;

    @Override
    public void init() throws ServletException {
        tweetDao = new TweetDao();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentUserLogin = ServletUtils.getUserLoginFromSession(req).toUpperCase();
        String tweetId = req.getParameter("tweetId");
        tweetDao.deleteTweet(currentUserLogin, Long.parseLong(tweetId));
        req.getRequestDispatcher("messages").forward(req, resp);
    }
}