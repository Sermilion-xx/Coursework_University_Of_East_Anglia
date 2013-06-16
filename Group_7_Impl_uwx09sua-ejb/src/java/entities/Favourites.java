/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import other.ArticleEvent;
import other.ArticleListener;

/**
 *
 * @author azamatibragimov
 */
@Entity
@Table(name = "Favourites")
public class Favourites implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "favourites_id",nullable=false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",nullable=false)
    private Users user;
    
    @OneToOne
    @JoinColumn(name = "fav_articles", unique=true,nullable=false)
    private Article fav_articles;
    
    @Column(name = "fav_updated")
    private boolean updated;

    public Favourites() {}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Boolean getUpdated(){
        return this.updated;
    }
    
    public void setUpdated(Boolean updated){
        this.updated=updated;
    }
    /**
     * @return the user
     */
    public Users getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(Users user) {
        this.user = user;
    }

    
    public Article getFavArticle() {
        return this.fav_articles;
    }
    
    public void setFavArticle(Article articles) {
        this.fav_articles=articles;
    }
    


    
    
    

    
}
