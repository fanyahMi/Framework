<%-- 
    Document   : Dept
    Created on : 3 avr. 2023, 19:35:33
    Author     : Hasinjo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Dept" %>
<%@ page import="model.Employer" %>
<%@ page import="java.util.List" %>

<%
    List<Employer> data = (List<Employer>)request.getAttribute("liste_emp");
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
    <%
        for (Employer employer : data) {
            out.println("<p>"+employer.getLibelle()+"</p>");
        }
    %>
    
    </body>
</html>
