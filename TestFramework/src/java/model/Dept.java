/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import annotation.Parameter;
import annotation.Scop;
import annotation.URLannotation;
import etu1837.framework.FileUpload;
import etu1837.framework.ModelView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Fanyah
 */
@Scop(scop = "singleton")
public class Dept {
    private String namDept;
    private Integer numero;
    private FileUpload fileUpload;
 
    @URLannotation(url = "detail-dept")
    public ModelView detail(@Parameter(name = "id") String testtt, @Parameter(name = "par")String aa){
        List<Employer> all = new ArrayList<>();
        Employer p =  new Employer(1, "Hasinjo");
        all.add(p);
        p = new Employer(2,aa);
        all.add(p);
        ModelView view = new ModelView();
        HashMap<String, Object> data = new HashMap<>();
        data.put("liste_emp", all);
        view.setPage("../Dept.jsp");
        view.setData(data);
        return view;
    }
    
    @URLannotation(url = "Dept-ajoute")
    public ModelView ajoutdept(){
        ModelView view = new ModelView();
        view.setPage("../Ajoutedept.jsp");
        return view;
    }
 
    @URLannotation(url = "Dept-findall")
    public ModelView findAll(){
        List<Employer> all = new ArrayList<>();
       
        Employer p =  new Employer(1, "Hasinjo");
        all.add(p);
        p = new Employer(2,"Toavina");
        all.add(p);
        ModelView view = new ModelView();
        HashMap<String, Object> data = new HashMap<>();
        data.put("liste_emp", all);
        view.setPage("../Dept.jsp");
        view.setData(data);
        return view;
        
    }
    @URLannotation(url = "insert-dept")
    public ModelView insert_dept() throws Exception{
        List<Employer> all = new ArrayList<>();
        Employer p =  new Employer(1, "Hasinjo");
        all.add(p);
        p = new Employer(2,this.getNamDept());
        
        try {
            this.getFileUpload().uploadFile("D:\\hasinjo");
        } catch (Exception e) {
            throw e;
        }
        
        all.add(p);
        ModelView view = new ModelView();
        HashMap<String, Object> data = new HashMap<>();
        data.put("liste_emp", all);
        view.setPage("../Dept.jsp");
        view.setData(data);
        return view;
    }
    
    @URLannotation(url = "AjoutDept")
    public ModelView affichage_insertDept(){
        ModelView view = new ModelView();
        view.setPage("../Ajoutedept.jsp");
        return view;
    }
    
    
    
    
    public String getNamDept() {
        return namDept;
    }

    public void setNamDept(String namDept) {
        this.namDept = namDept;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = Integer.parseInt(numero);
    }

    public FileUpload getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(FileUpload fileUpload) throws Exception  {
        if(fileUpload == null){
            throw new Exception("Impossible d'enregister ce ficher");
        }
        this.fileUpload = fileUpload;
    }
    
    
}
