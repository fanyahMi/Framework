/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etu1837.framework;

import java.util.HashMap;

/**
 *
 * @author Hasinjo
 */
public class ModelView {
    private String page;
    private HashMap<String, Object> data;
    
    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
    
    
    
    
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    
}
