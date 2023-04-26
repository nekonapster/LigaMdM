<%-- 
    //vista: pagina del login del administrador.
    Document   : Login
    Created on : 26 abr 2023, 11:04:45
    Author     : martin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <title>Login admin</title>
    </head>
    <html lang="en">

        <body>
            <div class="container" style="position: absolute;left: 0;right: 0;top: 50%;transform: translateY(-50%);-ms-transform: translateY(-50%);-moz-transform: translateY(-50%);-webkit-transform: translateY(-50%);-o-transform: translateY(-50%);">
                <div class="row justify-content-center">
                    <div class="col-md-10 col-lg-9 col-xl-9 col-xxl-7">
                        <div class="card shadow-lg o-hidden border-0 my-5">
                            <div class="card-body p-0">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="p-5">
                                            <div class="text-center">
                                                <h4 class="text-dark mb-4">Administrador</h4>
                                            </div>
                                            <form class="user" action='LoginServlet' method="POST">
                                                <div class="mb-3"><input id="usu" class="form-control form-control-user" type="text" aria-describedby="Usuario" placeholder="Usuario" name="usu" required /></div>
                                                <div class="mb-3"><input class="form-control form-control-user" type="password" placeholder="Password" name="pass" required /></div>
                                                <div class="row mb-3">
                                                    <p id="errorMsg" class="text-danger" style="display: none;">Paragraph</p>
                                                </div><button id="submitBtn" class="btn btn-primary d-block btn-user w-100" type="submit">Login</button>
                                                <hr />
                                            </form>
                                                <a href="index.jsp" class="btn btn-dark btn-block" >Volver</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    </html>
