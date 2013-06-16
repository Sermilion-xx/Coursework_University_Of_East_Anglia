/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import ejb.StatelessBeanRemote;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.*;
import other.ArticleListener;
/**
 *
 * @author azamatibragimov
 */
@Entity
@Table(name = "Users")
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Long id;

    @Column(name="fullname", length=40,nullable=false)
    private String name;
//Date of Birth    
    @Column(name="dob",nullable=false)
    private String dob;
    
    @Column(name="password", nullable=false, length=50)
    private String password;
    
    @Column(name="email", unique=true, length=50,nullable=false)
    private String email;
    
    @Column(name="type",nullable=false)
    private String type;
    
    @Column(name="rating")
    private Double rating;
    
    @Column(name="last_login")
    private Timestamp lastlogin;
    
    public Users(){
        
    }
    public Users(Long id, String dob, String email, Timestamp last_login,  String fullname, String password, Double rating, String type ){
        this.id=id;
        this.email=email;
        this.name=fullname;
        this.rating=rating;
        this.type=type;
        this.lastlogin=last_login;
        this.dob=dob;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

      /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    public String getDob(){
        return this.dob;
    }
    
    public void setDob(String dob){
        this.dob=dob;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the lastlogin
     */
    public Timestamp getLastlogin() {
        return lastlogin;
    }

    /**
     * @param lastlogin the lastlogin to set
     */
    public void setLastlogin(Timestamp lastlogin) {
       
        this.lastlogin = lastlogin;
    }

    
    
    
       
}
