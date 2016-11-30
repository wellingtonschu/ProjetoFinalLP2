<%-- 
    Document   : index
    Created on : 01/11/2016, 08:24:24
    Author     : Alex
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String logado = (String) session.getAttribute("logado");
%>

<c:if test="${logado == null}">
    <c:redirect url="login.jsp"/>
</c:if>

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
            <c:param name="ativa_index" value="active"/>
        </c:import>
        
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="text-center">Histórico de irrigação</h1>
                    </div>
                </div>
            </div>
        </div>
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th style="width: 200px">Localização</th>
                                    <th>Data</th>
                                    <th>Horário</th>
                                    <th>Situação</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td style="width: 200px">-27.36, 47.21</td>
                                    <td>11/10/2016</td>
                                    <td>08:37</td>
                                    <td style="width: 200px">Irrigando</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px">34.43, 78.12</td>
                                    <td>11/10/2016</td>
                                    <td>08:37</td>
                                    <td style="width: 200px">Irrigando</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px">67.09, -21.23</td>
                                    <td>11/10/2016</td>
                                    <td>08:37</td>
                                    <td style="width: 200px">Não irrigando</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px">12.54, 21.76</td>
                                    <td>11/10/2016</td>
                                    <td>08:37</td>
                                    <td style="width: 200px">Irrigando</td>
                                </tr>
                                <tr>
                                    <td style="width: 200px">-45.98, 98.90</td>
                                    <td>11/10/2016</td>
                                    <td>08:37</td>
                                    <td style="width: 200px">Não irrigando</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <c:import url="rodape.jsp" />
    </body>
</html>
