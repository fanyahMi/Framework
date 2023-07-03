/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitaire;

import annotation.URLannotation;
import etu1837.framework.Mapping;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 *
 * @author Fanyah
 */
public class Utilitaire {
 
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
