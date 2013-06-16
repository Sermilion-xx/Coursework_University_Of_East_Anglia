/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author DinaraBerdysheva
 */
@Entity
@Table(name = "User_rating")
public class UserRating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "rated_user")
    private Long ratedUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getRatedUserId() {
        return this.ratedUser;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public void setRatedUserId(Long id) {
        this.ratedUser = id;
    }

    public UserRating(long ratedUser, long userId) {
        this.ratedUser = ratedUser;
        this.userId = userId;
    }
    
     public UserRating() {
      
    }
}
