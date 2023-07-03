<%-- 
    Document   : Ajoutedept
    Created on : 29 avr. 2023, 06:22:59
    Author     : Hasinjo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un département</title>
</head>
<body>
    <form action="insert-dept" method="post" enctype="multipart/form-data">
        <label for="namDept">Nom du département:</label>
        <input type="text" id="namDept" name="namDept" required><br>
        
        <label for="numero">Numéro:</label>
        <input type="text" id="numero" name="numero" required><br>
        
        <label for="fileUpload">Fichier:</label>
        <input type="file" id="fileUpload" name="fileUpload" required><br>
        
        <input type="submit" value="Valider">
    </form>
</body>
</html>
