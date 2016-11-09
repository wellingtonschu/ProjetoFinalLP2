<%-- 
    Document   : localizacao_list
    Created on : 08/11/2016, 10:04:03
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

        <h3>Lista de Localizações</h3>
        <c:if test="${locais==null}">
            <h2>Lista vazia</h2>
        </c:if>
        <c:if test="${locais!=null}">
            <table class="table table-hover">
                <thead>
                <th>ID</th>
                <th>ID Arduíno</th>
                <th>Localização</th>
            </thead>
            <c:forEach items="${locais}" var="o">
                <tr>
                    <td><c:out value="${o.id}"/></td>
                    <td><c:out value="${o.idArduino}"/></td>
                    <td><c:out value="${o.local}"/></td>
                    <td>
                        <a href="LocalServlet?acao=selecionar&id=${o.id}">[s]</a>
                        <a href="LocalServlet?acao=remover&id=${o.id}">[e]</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <div>
        <a href="localizacao.jsp"><input type="button" value="Novo"></a>
    </div>

    <c:import url="rodape.jsp" />
</body>
</html>
