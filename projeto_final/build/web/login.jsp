<%-- 
    Document   : login
    Created on : 01/11/2016, 10:02:29
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="js/jquery-3.1.1.min"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="css\style.css" rel="stylesheet" type="text/css">
        <title>Login Sistema de Irrigação</title>
    </head>
    <body>

        <%@include file="topo.jsp"%>

        <div class="container" style="width: 400px">

            <form class="form-signin" method="POST" action="LoginServlet">
                <h2 class="form-signin-heading">Login</h2>
                <label for="txtLogin" class="sr-only">Login</label>
                <input type="text" id="txtLogin" name="login" class="form-control" required autofocus>
                <br/>
                <label for="txtSenha" class="sr-only">Password</label>
                <input type="password" id="txtSenha" class="form-control" name="senha" required autofocus>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Mantenha-se conectado
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit" name="acao" value="logar">Entrar</button>
            </form>

            <br/>

            <c:if test="${erro == 1}">
                <div class="alert alert-danger">
                    <strong>Login ou senha inválido(s)</strong> 
                </div>
            </c:if>

        </div> <!-- /container -->

        <c:if test="${erro == 0 || erro == null}">
            <c:import url="rodape.jsp">
                <c:param name="bottom" value="0"/>
            </c:import>
        </c:if>
        <c:if test="${erro == 1}">
            <c:import url="rodape.jsp">
                <c:param name="bottom" value="auto"/>
            </c:import>
        </c:if>
    </body>
</html>
