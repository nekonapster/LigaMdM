<%-- 
    //vista: pagina principal, desde aca podemos movernos hacia donde querramos administrador o usuarios tipo "arbitros"
    Document   : index
    Created on : 26 abr 2023, 19:45:10
    Author     : martin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Recuperacion Junio MdM</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- IMPORT BOOTSTRAP 5.0-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    </head>
    
    <body>
    
        
        <div class="container d-flex align-items-center justify-content-center" style="height: 100vh;">
    <div class="card" style="width: 30rem;">
        <div class="card-body">
            <h5 class="card-title text-center">Proyecto recuperación junio Martín di Marco</h5>
            <p class="card-text text-center">La siguiente aplicación Java Web esta basada en JavaEE, jsp, jpa y servlets. Para comenzar puede seleccionar el tipo de login que quiera hacer.</br> Todos los derechos reservados por el autor. <strong>Nekonapster©2023</strong></p>
            <div class="d-grid">
                <a href="loginAdmin" class="btn btn-primary btn-block" >Administrador</a>
            </div>
            </br>
            <div class="d-grid">
                <a href="loginUser" class="btn btn-primary btn-block">Arbitro</a>
            </div>
            </br>
            <div class="d-grid">
                <a href="testPage" class="btn btn-primary btn-block">Usuarios sin registro</a>
            </div>
        </div>
    </div>
</div>

    </body>
        
        
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</html>
