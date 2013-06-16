/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Article;
import entities.ArticleRating;
import entities.Comment;
import entities.Favourites;
import entities.History;
import entities.Tags;
import entities.UserRating;
import entities.Users;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import other.ArticleEvent;
import other.ArticleListener;

@Stateless
public class StatelessBean implements StatelessBeanRemote, ArticleListener {

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;

    @Override
    public Long registerUser(Users user) {
        Long id = null;
        em.persist(user);
        id = user.getId();
        return id;
    }

    @Override
    public boolean login(Users user) {
        try {

            final String qq = "UPDATE Users u SET last_login = '" + user.getLastlogin() + "' WHERE u.email='" + user.getEmail() + "'";
            final String q = "SELECT password FROM Users e WHERE e.email='" + user.getEmail() + "'";
            String password = "";
            List obj = (List) em.createQuery(q).getResultList();
            if (!obj.isEmpty()) {
                password = (String) obj.get(0);

            }
            if (password.equals(user.getPassword())) {
                em.createQuery(qq).executeUpdate();
                return true;
            } else {
                return false;
            }
        } catch (NoResultException nre) {
            return false;
        }

    }
    
    
    @Override
    public Users getUserById(Long id){
        
        final String q = "SELECT u FROM Users u WHERE user_id='" + id + "'";
         Users user = (Users) em.createQuery(q).getSingleResult();
        return user;
    }



    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Long createArticle(Article article) {
        Long id = null;
        em.persist(article);
        return article.getId();
    }

    @Override
    public List<Article> getAllAtricles() {
        List<Article> result = null;
        try {
            final String q = "SELECT article FROM Article as article";
            result = em.createQuery(q).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
        return result;
    }

    @Override
    public List<Favourites> getFavourites(Users user) {
        final String q = "SELECT f FROM Favourites f WHERE user_id=" + user.getId() + "";
        List<Favourites> favArticles = (List<Favourites>) em.createQuery(q).getResultList();
        return favArticles;
    }

    @Override
    public Favourites getFavourites2(Users user, Article article) {
        final String q = "SELECT f FROM Favourites f WHERE user_id=" + user.getId() + "AND fav_articles=" + article.getId() + "";
        Favourites favArticles = (Favourites) em.createQuery(q).getSingleResult();
        return favArticles;
    }



    @Override
    public boolean checkFavourite(Favourites article) {
        try {
            final String q = "SELECT f FROM Favourites f WHERE f.fav_articles=" + article.getFavArticle().getId() + " AND user_id=" + article.getUser().getId() + "";
            Object qq = em.createQuery(q).getSingleResult();

            if (qq != null) {
                return true;
            } else {
                return false;
            }
        } catch (NoResultException nre) {
            return false;
        }
    }

    @Override
    public Article getArticleById(Long id) {
        try {
            //final String q = "SELECT a FROM Article a  WHERE article_id=" + id+"";
            //Article article = (Article) em.createQuery(q).getSingleResult();
            Article article = em.find(Article.class, id);
            return article;
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public boolean createTag(Tags tag) {
        try {
            em.persist(tag);
            Long id = tag.getId();
            return true;

        } catch (EntityExistsException nre) {
            return false;
        }

    }

    @Override//name and type in case if super and some subtag has same name
    public Tags getTagsDB(Long tag_id, Long type) {
        Tags result = null;
        try {
            final String q = "SELECT e FROM Tags e WHERE e.tag_id='" + tag_id + "' AND e.tag_type=" + type + "";
            result = (Tags) em.createQuery(q).getSingleResult();
        } catch (NoResultException nre) {
        }
        return result;
    }
    


    @Override
    public List<Tags> loadTags(Long id, Long type) {
        List<Tags> result = null;
        try {
            String q="";
            Integer t = (int) (long) type;
            switch(t){
                case 0: q = "SELECT f FROM Tags f WHERE parent_tag2_tag_id=" + id + " AND parent_tag_tag_id=null";
                        break;
                case 1: q = "SELECT f FROM Tags f WHERE parent_tag_tag_id=" + id + "";
                        break;
            }
                      
            result = (List<Tags>) em.createQuery(q).getResultList();
        } catch (NoResultException nre) {
        }
        return result;
    }

    @Override
    public boolean addToFavourites(Favourites article) {
        try {
            em.persist(article);
            return true;

        } catch (EntityExistsException nre) {
            return false;
        }
    }

    @Override
    public boolean RemoveFromFavourites(Favourites article) {
        try {
            em.remove(em.merge(article));

            return true;

        } catch (EntityNotFoundException nre) {
            System.out.println(nre);
            return false;
        }
    }

    @Override
    public List<Article> searchArticle(String title) {
        List<Article> result = null;
        try {
            final String q = "SELECT article FROM Article as article where UPPER(a_content) LIKE '%" + title + "%'"
                    + " OR a_name LIKE '%" + title + "%'";
            result = em.createQuery(q).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
        return result;
    }

    @Override
    public List<Favourites> loadListeners(Article article) {
        try {
            final String q = "SELECT f FROM Favourites f WHERE fav_articles=" + article.getId() + "";
            List<Favourites> favArticles = (List<Favourites>) em.createQuery(q).getResultList();
            return favArticles;
        } catch (NoResultException nre) {
            return null;
        }
    }
    

    @Override
    public Article lockUnlockArticle(Long id) {
        Article article = null;
        try {
            article = em.find(Article.class, id);
            if(article.isLocked()==false) {
                article.setLocked(true);
            }else{
                article.setLocked(false);
            }      
            return article;
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    
    @Override
    public Users editProfile(Users user) {

        Users newuser = null;
        try {
            newuser = em.find(Users.class, user.getId());
            newuser.setName(user.getName());
            newuser.setDob(user.getDob());
            return newuser;
        } catch (NoResultException nre) {
            return null;
        }
    }

    
    
    

//----------------------------------Observer Pattern Code----------------------------------------//
    @Override
    public Article fireEditArticleEvent(Article art, StatelessBeanRemote statelessb, Users user) {
        ArticleEvent artEvent = new ArticleEvent(art);
        
        try {
            Article article = em.find(Article.class, art.getId());
            article.setName(art.getName());
            article.setContent(art.getContent());
            article.setDate_modified(art.getDate_modified());
            article.setParentTag2(art.getParentTag2());
            article.setParentTag(art.getParentTag());
            article.setTag(art.getSubTag());
            article.setRating(art.getRating());
em.flush();
            List<Favourites> listeners = statelessb.loadListeners(art);
            if (!listeners.isEmpty()) {
                for (Favourites listener : listeners) {
                    statelessb.update(art, listener.getUser());
                }
            }
            return article;
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public Favourites update(Article fav, Users user) {
        final String q = "SELECT f FROM Favourites f WHERE fav_articles=" + fav.getId() + " AND user_id=" + user.getId() + "";
        Favourites favArticles = (Favourites) em.createQuery(q).getSingleResult();
        em.merge(favArticles).setUpdated(true);
        return favArticles;
    }
    
    @Override
    public void removeUpdated(Users user) {
        final String q = "SELECT f FROM Favourites f WHERE user_id=" + user.getId() + "";
        List<Favourites> favArticles = (List<Favourites>) em.createQuery(q).getResultList();
        for(int i=0;i<favArticles.size();i++){
        em.merge(favArticles.get(i)).setUpdated(false);
        }
    }
    
    @Override
    public Favourites getFavourites3(Article article, Users user) {
        final String q = "SELECT f FROM Favourites f WHERE fav_articles=" + article.getId() + " AND user_id=" + user.getId() + "";
        Favourites favArticles = (Favourites) em.createQuery(q).getSingleResult();
        return favArticles;
    }
//----------------------------------Observer Pattern Code----------------------------------------//

    @Override
    public boolean archiveArticle(History art){
    try {
            em.persist(art);
            return true;

        } catch (EntityExistsException nre) {
            return false;
        }
    }
    
    
  //:::::::::::::::::::::::::::::::::::::::
    // ::::::::::::: Orkhan :::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::
    
    @Override
       public Article getArticleByID(int id) {
        try {
            final String q = "SELECT a FROM Article a  WHERE article_id=" + id+"";
            Article article = (Article) em.createQuery(q).getSingleResult();
            return article;
        } catch (NoResultException nre) {
            return null;
        }
    }
     
       @Override
       
    public Users getUserByID(int id) {
        try {
            final String q = "SELECT a FROM Users a  WHERE user_id=" + id+"";
            Users user = (Users) em.createQuery(q).getSingleResult();
            return user;
        } catch (NoResultException nre) {
            return null;
        }
    }
       
    @Override
    public Long AddComment(Comment comment) {
        Long id = null;
        em.persist(comment);
        return comment.getId();
    }   
    
     @Override
    public boolean removeComment(Comment comment) {
        Comment cmt =null;
        cmt = em.find(Comment.class,comment.getId());  
      if (cmt !=null){
              em.remove(cmt);
              return true;
      }  
      else{
          return false;
      }
     }   
    
    //Orkhan 
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
    public Comment getCommentByID(int commentID){
        Comment comment = null; 
        try{
        final String q = "Select comment FROM Comment as comment WHERE comment_id="+commentID+"";
        comment = (Comment) em.createQuery(q).getSingleResult();
        return comment;
        } catch (NoResultException nre) {
            return null;
        } catch (IndexOutOfBoundsException nre) {
            return null;
        }  
    
    
    }
    
    @Override
     public List<History> getEditedArticleById(int id){
        List<History> history = null;
            try{
        final String q = "Select history FROM History as history WHERE article_id="+id+"";
        history = em.createQuery(q).getResultList();
        return history;
        } catch (NoResultException nre) {
            return null;
        } catch (IndexOutOfBoundsException nre) {
            return null;
        }        
        
        }

    //::::::::::::::::::::


 
	public List<Users> getAllUsers(){
        List<Users> result = null;
        try {
            final String q = "SELECT user FROM Users as user" ;
            result = em.createQuery(q).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
        return result;
    }
	
	public void updateUserRating(double rating,Users user){
        //List<Article> result = null;
		double newRating = (user.getRating()+rating);
        try {
			final String qq = "UPDATE Users u SET rating = '" + newRating + "' WHERE u.email='" + user.getEmail() + "'";
			//  final String q = "SELECT password FROM Users e WHERE e.email='" + user.getEmail() + "'";
            String password = "";
            em.createQuery(qq).executeUpdate();
        } catch (NoResultException nre) {
            
        }
        
    }
    
    
    
    
	public void updateUserStatus(Users user, String status){
		
        try {
			final String qq = "UPDATE Users u SET type = '" + status + "' WHERE u.email='" + user.getEmail() + "'";
			//  final String q = "SELECT password FROM Users e WHERE e.email='" + user.getEmail() + "'";
            String password = "";
            em.createQuery(qq).executeUpdate();
        } catch (NoResultException nre) {
            
        }
        
    }
	
	
	
    @Override
    public boolean addToUserRating(UserRating userRating) {
        try {
            em.persist(userRating);
            return true;
			
        } catch (EntityExistsException nre) {
            return false;
        }
    }
    
    
    @Override
	public UserRating checkRating(long ratedUser, long user_id){
		UserRating result = null;
        try {
            final String q = "SELECT userRating FROM UserRating as userRating where rated_user='"+ ratedUser+"'"
			+ " AND user_id ='"+user_id+"'" ;
			result = (UserRating) em.createQuery(q).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
        return result;
    }

    @Override
    public void addArticleRating(ArticleRating ar){
            ArticleRating result = null;
       try {
           final String q = "SELECT articlerating FROM ArticleRating as articlerating where article_id=" + ar.getArticleId()+ " AND " 
                   + "user_id=" + ar.getUserId();
                   result = (ArticleRating) em.createQuery(q).getSingleResult();
           if(result==null){
            
           }
           
           else {
              final String qq = "UPDATE ArticleRating u SET rated = '" + ar.getRated() + "' where article_id=" + ar.getArticleId()+ " AND " 
                   + "user_id=" + ar.getUserId();
            em.createQuery(qq).executeUpdate();
           }
			
        } catch (EntityExistsException nre) {
           
        } catch (NoResultException nre) {
            em.persist(ar);
            
        }
    }
    
    

}
