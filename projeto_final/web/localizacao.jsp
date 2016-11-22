<%-- 
    Document   : localizacao
    Created on : 08/11/2016, 00:00:49
    Author     : Alex
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="js/jquery-3.1.1.min"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="css\style.css" rel="stylesheet" type="text/css">
        <title>JSP Page</title>
    </head>
    <body>
        <c:import url="topo.jsp">
            <c:param name="ativa_localizacao" value="active"/>
        </c:import>

        <div class="container" style="width: 400px">

            <h1 style="text-align: center;">Cadastro de Localização</h1>

            <form class="form-signin" method="POST" action="LocalServlet">

                <input id="txtId" type="hidden" name="id" value="${localizacao.id}"/>
                <label for="txtIdArduino" >ID Arduíno</label>
                <input value="${localizacao.idArduino}" type="text" id="txtIdArduino" 
                       name="idArduino" class="form-control" required autofocus/>
                <label for="txtLocalizacao">Localização</label>
                <input value="${localizacao.local}" type="text" id="txtLocalizacao" class="form-control" name="local" required autofocus/>

                <br/>

                <div style="text-align: right;">
                <c:if test="${localizacao.id == null}">
                    <button class="btn btn-primary" type="submit" name="acao" value="inserir">
                        Salvar
                    </button>                        
                </c:if>
                <c:if test="${localizacao.id != null}">
                    <button class="btn btn-primary" type="submit" name="acao" value="alterar">
                        alterar
                    </button>
                </c:if>
                <button class="btn btn-default" type="reset">cancelar</button>
                </div>
            </form>

        </div>

        

        <!--<c:import url="rodape.jsp" />-->
    </body>
</html>
