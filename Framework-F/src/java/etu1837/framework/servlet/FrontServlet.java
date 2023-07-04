/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package etu1837.framework.servlet;

import etu1837.framework.FileUpload;
import etu1837.framework.Mapping;
import etu1837.framework.ModelView;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitaire.Utilitaire;


/**
 *
 * @author Fanyah
 */
@MultipartConfig
public class FrontServlet extends HttpServlet {


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private HashMap<String, Mapping> mappingUrls = new HashMap<>();
    private HashMap<String, Object> obj_singleton = new HashMap<>();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            utilitaire.Utilitaire utilitaire = new Utilitaire();
        /** Recherche du mapping a partir Hasmap  **/
            Mapping map = utilitaire.get_mapping(mappingUrls, request);
            if( map != null ){
            /*** La class et le methode utiliser ***/
                HashMap<String , Object> class_method =  utilitaire.get_class_method( map, obj_singleton );
            // La class    
                Object obj = class_method.get("object");
                String attribut_name = null;   
                Field[] attribut = obj.getClass().getDeclaredFields();
                for (Field field : attribut) {
                    attribut_name = utilitaire.capitalize(field.getName());
                       
                    if(field.getType().getName().equals("etu1789.framework.FileUpload")){
                        try {
                            FileUpload file_upload = new FileUpload();
                            Part filePart = request.getPart(field.getName());
                            file_upload.transform_fileupload(filePart);
                            obj.getClass().getMethod("set"+attribut_name, field.getType() ).invoke(obj, file_upload);
                        }catch (Exception e) { out.print(e.getMessage()); } 
                    }
                    if(request.getParameter(field.getName())!= null){
                        try {
                            obj.getClass().getMethod("set"+attribut_name, String.class).invoke(obj, request.getParameter(field.getName()));
                        } catch (Exception e) {
                            out.print(e.getMessage());
                        }
                    }
                }
                
            // Le method
                Method method = (Method)class_method.get("method");
                Parameter[] parametre = method.getParameters();
                ModelView view = null;
            
                if(parametre.length!=0){
                    Object[] arguments = new Object[parametre.length];
                    int count = 0;
                    for (Parameter parameter : parametre) {
                        if(parameter.isAnnotationPresent(annotation.Parameter.class)){
                            String val = (parameter.getAnnotation(annotation.Parameter.class)).name();
                            if(request.getParameter(val)!=null){
                                Class paramType  = parameter.getType();
                                arguments[count] = request.getParameter(val);
                            }else{
                                arguments[count] = null;
                            }
                            count++;    
                        }
                    }
                    try {
                         view = (ModelView) method.invoke(obj,arguments);
                    } catch (Exception e) {
                        out.print(e.getMessage());
                    }
                }
                else {
                    view = (ModelView) method.invoke(obj);
                }
                if(view != null){
                        try {
                        // Donner envoyer par model view
                            HashMap<String , Object> data = view.getData();
                            if( data != null){
                                for (Map.Entry<String, Object> dt : data.entrySet()) {
                                    String key = dt.getKey();
                                    Object data_view = dt.getValue();
                                    Class class_data = data_view.getClass();
                                    request.setAttribute(key, class_data.cast(data_view));
                                }
                            }
                            RequestDispatcher dispa = request.getRequestDispatcher(view.getPage());
                            dispa.forward(request, response);
                        } catch (Exception e) {
                            
                            out.print(e.getMessage());
                        }
                    }
                
                
            }else{
                out.print(" L' URL n'est pas trouvé ");
            }
            
            
        }
    }
    
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        String path = context.getRealPath("/");
        try{
            this.setMappingUrls( new Utilitaire().set_allMethodAnnotation(path,new File(path+"WEB-INF\\classes\\"),mappingUrls, obj_singleton));
        }catch(Exception e){
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public HashMap<String, Mapping> getMappingUrls() {
        return mappingUrls;
    }

    public void setMappingUrls(HashMap<String, Mapping> MappingUrls) {
        this.mappingUrls = MappingUrls;
    }

    public HashMap<String, Object> getObj_singleton() {
        return obj_singleton;
    }

    public void setObj_singleton(HashMap<String, Object> obj_singleton) {
        this.obj_singleton = obj_singleton;
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(FrontServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
