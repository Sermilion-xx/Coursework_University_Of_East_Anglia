/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NoResultException;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;
import other.ArticleEvent;
import other.ArticleListener;

/**
 *
 * @author azamatibragimov
 */
@Entity
@Table(name = "Articles")
public class Article implements Serializable {

    @PersistenceContext(unitName = "userPU")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_id")
    private Long id;
    @Column(name = "a_name", nullable = false)
    private String name;
    @Column(name = "a_content", length = 5000, nullable = false)
    private String content;
    @Column(name = "a_doc", nullable = false)
    private Timestamp date_created;
    @Column(name = "a_dlm")
    private Timestamp date_modified;
    @Column(name = "a_rating")
    private double rating;
    @Column(name = "a_locked", nullable = false)
    private boolean locked;
    @Column(name = "a_hidden", nullable = false)
    private boolean hidden;
    @Column(name = "a_updated")
    private boolean updated = false;
    private boolean is_favourite = false;
    @OneToOne
    @JoinColumn(name = "a_author")
    private Users author;
    @OneToOne
    @JoinColumn(name = "a_subtag", nullable = false)
    private Tags sub_tag;
    @OneToOne
    @JoinColumn(name = "parent_tag", nullable = false)
    private Tags parent_tag;
    @OneToOne
    @JoinColumn(name = "parent_tag2", nullable = false)
    private Tags parent_tag2;
    @Transient
    private List<ArticleListener> listeners;

    public Article() {
    }

    public Tags getParentTag2() {
        return this.parent_tag2;
    }

    public void setParentTag2(Tags tag) {
        this.parent_tag2 = tag;
    }

    public Tags getParentTag() {
        return this.parent_tag;
    }

    public void setParentTag(Tags tag) {
        this.parent_tag = tag;
    }

        public List<ArticleListener> getListeners() {
        return this.listeners;
    }

    public void addListeners(ArticleListener listener) {
        this.listeners.add(listener);
    }

    public void removeListeners(ArticleListener listener) {
        this.listeners.add(listener);
    }

    public boolean isFavourite() {
        return this.is_favourite;
    }

    public void setIsFavourite(boolean fav) {
        this.is_favourite = fav;
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

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the date_created
     */
    public Timestamp getDate_created() {
        return date_created;
    }

    /**
     * @param date_created the date_created to set
     */
    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

    /**
     * @return the date_modified
     */
    public Timestamp getDate_modified() {
        return date_modified;
    }

    /**
     * @param date_modified the date_modified to set
     */
    public void setDate_modified(Timestamp date_modified) {
        this.date_modified = date_modified;
    }

    /**
     * @return the rating
     */
    public double getRating() {
        return rating;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * @return the contributors
     */
    public Users getAuthor() {
        return author;
    }

    /**
     * @param contributors the contributors to set
     */
    public void setAuthor(Users author) {
        this.author = author;
    }

    public Boolean getUpdated() {
        return this.updated;
    }

    public void setUpdated(boolean update) {
        this.updated = update;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Article[ id=" + id + " ]";
    }

    /**
     * @return the tag
     */
    public Tags getSubTag() {
        return sub_tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(Tags tag) {
        this.sub_tag = tag;
    }

    /**
     * @return the hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @param hidden the hidden to set
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
