/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Article;
import entities.ArticleRating;
import entities.Comment;
import entities.Tags;
import entities.Users;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sermilion
 */
@Stateful(name="StatefulBean", mappedName="StatefulBean")
public class StatefulBean implements StatefulBeanRemote {

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;
    
   
    /**
     *
     * @param email
     * @return
     */
    @Override
    public Users getUser(String email) {
        final String q = "SELECT e FROM Users e WHERE e.email='"+email+"'";
        //Users user = null;
       Users result = (Users) em.createQuery(q).getSingleResult(); 
       Users user = new Users(result.getId(),result.getDob(),result.getEmail(),result.getLastlogin(),(String)result.getName(),result.getPassword(),(Double)result.getRating(),(String)result.getType());
       
       user.setPassword(" ");
       // Users user = new Users();
        
        
        return user;
    }
     @Override
    public List<Tags> getAllTags(Long type){
         try{
        final String q = "SELECT tags FROM Tags as tags WHERE tag_type="+type+"";
        List tags = em.createQuery(q).getResultList();
        return tags;
        } catch (NoResultException nre) {
            return null;
        } catch (IndexOutOfBoundsException nre) {
            return null;
        }        
    }
     
     @Override
    public Tags getAllTagsByName(String type, Long _tag_type){
         try{
        final String q = "SELECT t FROM Tags t WHERE tag_name='"+type+"' AND tag_type="+_tag_type+"";
        Tags tags = (Tags)em.createQuery(q).getSingleResult();
        return tags;
        } catch (NoResultException nre) {
            return null;
        } catch (IndexOutOfBoundsException nre) {
            return null;
        }       
    }
     
    @Override
    public List<Article> getArticleByTag(Long tag, Long type) {
        List result = null;
        try {
            String q="";
            Integer t = (int) (long) type;
            switch(t){
                case 0: q = "SELECT f FROM Article f WHERE parent_tag2=" + tag + "";
                        break;
                case 1: q = "SELECT f FROM Article f WHERE parent_tag=" + tag + "";
                        break;
                case 2: q = "SELECT f FROM Article f WHERE a_subtag=" + tag + "";
                        break;
            }
            
        result = em.createQuery(q).getResultList();
        } catch (NoResultException nre) {
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }catch (NullPointerException e) {
            return null;
        }
        if(result!=null) {
            return result;
        }
        else {
            return null;
        }
    }
    

    //::::::::::::Orkhan:::::::::::
     @Override
    public List<Comment> getCommentsByID(int articleID){
        List<Comment> comments = null; 
        try{
        final String q = "SELECT comment FROM Comment as comment WHERE article_id="+articleID+"";
        comments = em.createQuery(q).getResultList();
        return comments;
        } catch (NoResultException nre) {
            return null;
        } catch (IndexOutOfBoundsException nre) {
            return null;
        }  
     }
     
      @Override
    public String getArticleRatingByUser(Long userid, Long articleid){
        List<ArticleRating> articlerating = null; 
        try{
        final String q = "SELECT rated FROM ArticleRating as articlerating WHERE article_id="+articleid+" AND " + "user_id="+userid;
        articlerating = em.createQuery(q).getResultList();
        String rated = articlerating.toString();
        return rated;
        } catch (NoResultException nre) {
            return null;
        } catch (IndexOutOfBoundsException nre) {
            return null;
        }  
     }
}
