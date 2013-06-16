/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import entities.Article;
//----------------------------------Observer Pattern Code----------------------------------------//
/**
 *
 * @author Sermilion
 */
public class ArticleEvent {
    private Article article;
    
    public ArticleEvent(Article art){
        this.article=art;
    }
    
    public Article getArticle(){
        return this.article;
    }
}
//----------------------------------Observer Pattern Code----------------------------------------//