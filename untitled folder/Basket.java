/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.*;
import java.util.ArrayList;
import javax.ejb.Stateful;

/**
 *
 * @author azamatibragimov
 */
@Stateful(name = "Basket", mappedName = "Basket")
public class Basket implements BasketRemote {

    private Users user;
    private ArrayList<Article> articles;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
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
    public boolean removeArticle(int index) {
        articles.remove(index);
        return true;
    }

    @Override
    public void setUser(Users user) {
        this.user = user;
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
    public void setBasket(Users user) {
        this.user = user;
        articles = new ArrayList<Article>();
    }
}
