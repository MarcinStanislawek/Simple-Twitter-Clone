package dao;

import model.Tweet;
import model.User;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class TweetDao extends AbstractDao {
    UserDao userDao = new UserDao();

    public void addTweet(String userLogin, String message) {
        User author = userDao.getUserByLogin(userLogin);
        Tweet tweet = new Tweet();
        tweet.setMessage(message);
        tweet.setPublishDate(new Date());
        tweet.setAuthor(author);
        hibernateUtil.save(tweet);
    }

    public void deleteTweet(String userLogin, long tweetId) {
        User loggedUser = userDao.getUserByLogin(userLogin);
        Tweet tweetToDelete = entityManager.find(Tweet.class, tweetId);
        if(tweetToDelete.getAuthor().equals(loggedUser)) {
            hibernateUtil.delete(Tweet.class, tweetId);
        }
    }

    public List<Tweet> getFollowedTweets(String userLogin) {
        User loggedUser = userDao.getUserByLogin(userLogin);
        Set<User> followedUsers = loggedUser.getFollows();
        List<User> followedUsersAndCurrentUser = new ArrayList<>(followedUsers);
        followedUsersAndCurrentUser.add(loggedUser);
        Query query = entityManager.createQuery("select t from Tweet t where t.author in :followedUsersAndCurrentUser");
        query.setParameter("followedUsersAndCurrentUser", followedUsersAndCurrentUser);
        return query.getResultList();
    }
}