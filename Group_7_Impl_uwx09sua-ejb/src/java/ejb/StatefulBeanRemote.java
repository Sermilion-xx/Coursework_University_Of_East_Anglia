/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Article;
import entities.Comment;
import entities.Tags;
import entities.Users;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sermilion
 */
@Remote
public interface StatefulBeanRemote {
    Users getUser(String email);
    List<Tags> getAllTags(Long type);
    List<Article> getArticleByTag(Long id, Long type);
    Tags getAllTagsByName(String type, Long tag_type);
     //Okrhan 
    List<Comment> getCommentsByID(int i);
    //
    String getArticleRatingByUser(Long userid, Long articleid);
   
}
