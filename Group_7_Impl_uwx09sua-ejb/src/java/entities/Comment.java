/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author azamatibragimov
 */
@Entity
@Table(name = "Comments")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "article_id", nullable=false)
    Article article;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable=false)
    Users user;
    
    @Column(name = "f_content")
    String content;
    
    @Column(name = "f_date")
    Date date;
    
    public Comment() {
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setArticleID(Article article){
            this.article=article;
    }
    
    public void setUserID(Users user){
            this.user=user;
    }
    
    public void setContent(String content){
            this.content = content;
    }
    
    public void setDate(Date date){
            this.date =date;   
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Comment[ id=" + id + " ]";
    }
    
}
