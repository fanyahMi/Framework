/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotation.URLannotation;
import etu1837.framework.ModelView;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fanyah
 */
public class Employer {
    private int id ;
    private String libelle;
    
    public Employer() {
    }

    public Employer(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    } 
    
    
    @URLannotation(url = "findall")
    public ModelView findAll(){
        List<Employer> all = new ArrayList<>();
       
        Employer p =  new Employer(1, "Hasinjo");
        all.add(p);
        ModelView view = new ModelView();
        view.setPage("index.jsp");
        return view;
    }
    
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
}
