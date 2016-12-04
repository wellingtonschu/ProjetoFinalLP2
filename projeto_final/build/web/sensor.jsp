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
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="css\style.css" rel="stylesheet" type="text/css">
        <title>JSP Page</title>
    </head>
    <body>
        <c:import url="topo.jsp">
            <c:param name="ativa_sensor" value="active"/>
        </c:import>

        <div class="container" style="width: 400px">

            <h1 style="text-align: center;">Cadastro de Sensores</h1>

            <form class="form-signin" method="POST" action="SensorServlet">

                <input id="txtId" type="hidden" name="id" value="${sensor.id}"/>
                <label for="textNome">Nome sensor</label>
                <input value="${sensor.nome}" type="text" id="textNome" name="nome" class="form-control" required autofocus/>

                <label for="txt">ID Arduino</label>
                <select class="form-control" id="comboTipo" size="1" name="localizacao">
                    <option value=""></option>
                    <c:forEach items="${localizacoes}" var="val"> 
                        <option value="${val.id}" ${sensor.localizacao.id == val.id ? 'selected' : ' '}><c:out value="${val.idArduino}" ></c:out></option>   
                    </c:forEach> 
                </select>

                <br/>

                <div style="text-align: right;">
                    <c:if test="${sensor.id == null}">
                        <button class="btn btn-success" type="submit" name="acao" value="inserir">
                            Salvar
                        </button>                        
                    </c:if>
                    <c:if test="${sensor.id != null}">
                        <button class="btn btn-success" type="submit" name="acao" value="alterar">
                            Alterar
                        </button>
                    </c:if>
                    <button class="btn btn-default" type="submit" name="acao" value="cancelar">Cancelar</button>
                </div>
            </form>

        </div>

        <br/>

        <c:import url="rodape.jsp">
            <c:param name="bottom" value="auto"/>
        </c:import>
    </body>
</html>
