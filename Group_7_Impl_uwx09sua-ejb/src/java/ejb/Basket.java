/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.*;
import java.util.ArrayList;
import javax.ejb.Stateful;

/**
 *dsfds
 * @author azamatibragimov
 */
@Stateful(name="Basket", mappedName="Basket")
public class Basket implements BasketRemote {

    private Users user;
    private ArrayList<Article> articles;
    
    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean inTheBasket (long id) {
        boolean result = false;
        for(int i=0; i<articles.size(); i++) {
            result = articles.get(i).getId() == id;
        }
        return result;
    }
    
    @Override
    public int getSize() {
        return articles.size();
    }

    @Override
    public boolean addArticle(Article article) {
        articles.add(article);
        return true;
    }
    

    @Override
    public boolean removeArticle(Article article) {
        articles.remove(article);
        return true;
        /* 
         * for(int i=0; i<articles.size(); i++) {
         *    if(articles.get(i).getId() == article.getId()) articles.remove(i);
         * }
         */
    }

    @Override
    public void setUser(Users user) {
        this.user=user;
    }

    @Override
    public Users getUser() {
        return this.user;
    }

    @Override
    public ArrayList<Article> getBasket() {
        return this.articles;
    }

    @Override
    public void setBasket() {
        articles = new ArrayList<Article>();
    }
    
    @Override
    public void remove() {
        articles = null;
    }
    /* Add method to remove basket when logout */
    

}
