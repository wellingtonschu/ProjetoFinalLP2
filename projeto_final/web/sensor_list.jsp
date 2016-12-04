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
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="css\style.css" rel="stylesheet" type="text/css">
        <title>Sensores</title>
    </head>
    <body>


        <!--Vai fazer com que mude o foco do botao-->
        <c:import url="topo.jsp">
            <c:param name="ativa_sensor" value="active"/>
        </c:import>

        <div class="row">
            <div class="col-md-12">
            <h3 style="text-align: center">Lista de Sensores</h3>
            <c:if test="${sensores==null}">
                <h2>Lista vazia</h2>
            </c:if>
            <c:if test="${sensores!=null}">

                <c:if test="${erro == 1}">
                    <div class="alert alert-danger">
                        <strong>Erro!</strong> Impossível exlcuir este Sensor, o mesmo está sendo utilizado em outro Registro!
                    </div>
                </c:if>
                </div>
            </div>
                
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-hover">
                        <thead>
                        <th style="width: 15%">ID</th>
                        <th style="width: 25%">Sensor</th>
                        <th style="width: 35%">ID Arduino</th>
                        <th style="width: 25%"></th>
                        </thead>
                        <c:forEach items="${sensores}" var="o">
                            <tr>
                                <td><c:out value="${o.id}"/></td>
                                <td><c:out value="${o.nome}"/></td>
                                <td><c:out value="${o.localizacao.idArduino}"/></td>
                                <td>
                                    <a href="SensorServlet?acao=selecionar&id=${o.id}"><input type="button" class="btn btn-primary"  value="Selecionar"></a>
                                    <a href="SensorServlet?acao=remover&id=${o.id}"><input type="button" class="btn btn-danger"  value="Excluir"></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>

            <div class="row" style="float: right; margin-right: 100px;">
                <a href="SensorServlet?acao=novoRegistro"><input type="button" class="btn btn-info"  value="Novo"></a>
            </div>

        </div>

        <br/>

        <c:if test="${tamanhoLista >= 2}">
            <c:import url="rodape.jsp">
                <c:param name="bottom" value="auto"/>
            </c:import>
        </c:if>


        <c:if test="${tamanhoLista < 2}">
            <c:import url="rodape.jsp">
                <c:param name="bottom" value="0"/>
            </c:import>
        </c:if>
    </body>
</html>
