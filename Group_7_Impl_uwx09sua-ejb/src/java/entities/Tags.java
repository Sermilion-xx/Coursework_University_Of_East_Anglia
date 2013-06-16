/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author azamatibragimov
 */
@Entity
@Table(name = "Tags")
public class Tags implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="tag_id")
    private Long tag_id;
   
    @Column(name="tag_name",nullable=false)
    private String tag_name;
    
    @Column(name="tag_descr",nullable=false)
    private String tag_descr;

    @OneToOne
    private Tags parent_tag;    
    //0-supertag 1-subsupertag 2- subtags
    @Column(name="tag_type")
    private int tag_type;
    
    @OneToOne
    private Tags parent_tag2;    
    //0-supertag 1-subsupertag 2- subtags

    
    
    public Tags(){
        
    }

    public Tags getParentTag2(){
        return this.parent_tag2;
    }
    
    public void setParentTag2(Tags tag){
        this.parent_tag2=tag;
    }

    
    public Long getId() {
        return tag_id;
    }

    public void setId(Long id) {
        this.tag_id = id;
    }
    
    public String getName() {
        return this.tag_name;
    }

    public void setName(String name) {
        this.tag_name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.tag_descr;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.tag_descr = description;
    }
    
    public Tags getParentTag(){
        return this.parent_tag;
    }
    
    public void setParentTag(Tags tag){
        if(this.tag_type==0){
            this.parent_tag=null;
        }else if(this.tag_type==1 && tag.getType()==0){
            this.parent_tag=tag;
        }else if(this.tag_type==2 && tag.getType()==1){
            this.parent_tag=tag;
        }else{
            this.parent_tag=this.parent_tag;
        }
    }
    
//    public boolean addTag(Tags tag){
//        
//            if(this.supertags.isEmpty() && tag.getType()==0){
//                this.supertags.add(tag); 
//                return true;
//            }else if(this.supertags.size()==1 && tag.getType()==1){
//                this.supertags.add(tag);  
//                return true;
//            }else if(this.supertags.size()==2 && tag.getType()==2){
//                this.supertags.add(tag);  
//                return true;
//            }else {
//                return false;
//            }
//            
//       
//    }
    
    public int getType(){
        return this.tag_type;
    }
    
    public void setType(int type){
        this.tag_type=type;
    }
//    public Tags getSupsupTag(){
//        return this.super_super_tag;
//    }
//    public void setSupsupTag(Tags tag){
//        this.super_super_tag=tag;
//    }
//    
//    public Tags getSuptag(){
//        return this.super_tag;
//    }
//    
//    public void setSuptag(Tags tag){
//        this.super_tag=tag;
//    }



}
