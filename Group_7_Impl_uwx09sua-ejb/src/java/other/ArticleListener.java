/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package other;


import entities.Article;
import entities.Favourites;
import entities.Users;

/**
 *
 * @author Sermilion
 */
public interface ArticleListener {
    /**
     *
     * @param art
     * @return
     */
//----------------------------------Observer Pattern Code----------------------------------------//
     Favourites update(Article fav,Users user);
//----------------------------------Observer Pattern Code----------------------------------------//
}
