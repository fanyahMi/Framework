/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitaire;

import annotation.URLannotation;
import etu1837.framework.Mapping;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fanyah
 */
public class Utilitaire {
    
    public  String capitalize(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
    
    /***** Sprint 5   ****/
    
    public String[] splitURL(HttpServletRequest request){
        String urldefault = request.getPathInfo();
        String[] taburl = urldefault.split("/");
        return taburl;
    }
    
    
     public  HashMap<String , Object> get_class_method(Mapping mapping) throws ClassNotFoundException{
        HashMap<String , Object> rep = new HashMap<>();
        Class class_utiliser = Class.forName(mapping.getClassName());
        rep.put("class", class_utiliser);
        Method[] liste_method = class_utiliser.getDeclaredMethods();
        for (Method method : liste_method) {
            if(method.getName().equals(mapping.getMethod())){
                rep.put("method", method);
                break;
            }
        }
        return rep;
    } 
     
     
    public Mapping get_mapping(HashMap<String , Mapping> liste_mapping, HttpServletRequest request) {
        String[] splits = splitURL(request);
        String annotation = splits[1];
        for (Map.Entry<String, Mapping> entry :  liste_mapping.entrySet()) {
                String key = entry.getKey();
                if(key.equals(annotation)){
                    return entry.getValue();
                }
        }
        return null;
    } 
     
    
    
    
    /*********************/
 
    public HashMap<String , Mapping> set_allMethodAnnotation( String path, File directori , HashMap<String, Mapping> mappingUrls) throws ClassNotFoundException{
        for (File file_details : directori.listFiles()) {
            if(file_details.isDirectory() == true){
                mappingUrls = set_allMethodAnnotation(path, file_details, mappingUrls);
            }else{
                if(file_details.getName().contains(".class")){
                    String name_class = file_details.toString().split("\\.")[0].replace(path+"WEB-INF\\classes\\" , "").replace("\\" , ".");
                    Class clazz = Class.forName(name_class);
                    Method[] List_functions = clazz.getDeclaredMethods() ;
                    for (Method method : List_functions) {
                        if(method.isAnnotationPresent(URLannotation.class)){
                            URLannotation annotation = method.getAnnotation(URLannotation.class);
                            mappingUrls.put(annotation.url(), new Mapping(name_class,method.getName()));
                        }
                    }
                }
            }
        }
        return mappingUrls;
    }
    
    
}
