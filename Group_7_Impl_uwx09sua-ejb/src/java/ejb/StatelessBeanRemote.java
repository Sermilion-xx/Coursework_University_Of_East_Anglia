/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.*;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;

/**
 *
 * @author azamatibragimov
 */
@Remote
public interface StatelessBeanRemote {
    Long registerUser(Users user) throws  PersistenceException;
    boolean login(Users user);
    Users getUserById(Long id);
    
    Long createArticle(Article article);
    Users editProfile(Users user);
    
    List<Article> getAllAtricles();
    Article getArticleById(Long id);
    Article lockUnlockArticle(Long id);
    
     boolean addToFavourites(Favourites article);
    public boolean RemoveFromFavourites(Favourites article);
    List<Favourites> getFavourites(Users user);
    public Favourites getFavourites2(Users user, Article article);
    Favourites getFavourites3(Article article, Users user);
    boolean checkFavourite(Favourites article);
    Article fireEditArticleEvent(Article art, StatelessBeanRemote statelessb,Users user);
    
    Tags getTagsDB(Long tag_id,Long type);
    List<Tags> loadTags(Long id, Long type);
    boolean createTag(Tags tag);
    
    List<Article> searchArticle(String title);
    List<Favourites> loadListeners(Article article);
    Favourites update(Article fav,Users user);
    void removeUpdated(Users user);
    boolean archiveArticle(History art);

    
    //::::::::::::Orkhan ::::::::::
    Users getUserByID(int id);
    List<Comment> getCommentsByID(int articleID);
    boolean removeComment(Comment comment);
    Comment getCommentByID(int commentID);
    Long AddComment(Comment comment);
    Article getArticleByID(int id);
    List<History> getEditedArticleById(int id);
    //::::::::::::::::::::::::::::::::::::::::::
    
    UserRating checkRating(long ratedUser, long user_id);
    boolean addToUserRating(UserRating userRating);
    void updateUserStatus(Users user, String status);
    void updateUserRating(double rating,Users user);
    List<Users> getAllUsers();

    public void addArticleRating(ArticleRating ar);
            
}
