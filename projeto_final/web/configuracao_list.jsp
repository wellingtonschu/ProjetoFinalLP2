<%-- 
    Document   : configuracao_list
    Created on : 03/12/2016, 16:28:28
    Author     : Alex Manoel Coelho <alexma_coelho@hotmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="css\style.css" rel="stylesheet" type="text/css">
        <title>Configurações</title>
    </head>
    <body>
        <!--Vai fazer com que mude o foco do botao-->
        <c:import url="topo.jsp">
            <c:param name="ativa_configuracao" value="active"/>
        </c:import>

        <div class="row">
            <div class="col-md-12">
                <h3 style="text-align: center">Lista de Configurações</h3>
                <c:if test="${configuracoes==null}">
                    <h2>Lista vazia</h2>
                </c:if>                
            </div>
        </div>



        <div class="row">
            <c:if test="${configuracoes!=null}">

                <c:if test="${erro == 1}">
                    <div class="alert alert-danger">
                        <strong>Erro!</strong> Impossível excluir está configuração, a mesma está sendo utilizado em outro Registro!
                    </div>
                </c:if>
                <div class="col-md-12">
                    <table class="table table-hover">
                        <thead>
                        <th>ID</th>
                        <th style="text-align: center;">Plantação</th>
                        <th style="text-align: center;" colspan="2">Umidade do Solo (KPa)</th>
                        <th style="text-align: center;" colspan="2">Umidade do Ar (%)</th>
                        <th style="text-align: center;" colspan="2">Temperatura (ºC)</th>
                        <th style="text-align: center;">ID Arduino</th>
                        <th></th>
                        </thead>
                        <tr>
                            <th colspan="2"></th>
                            <th style="text-align: center;">Mínima</th>
                            <th style="text-align: center;">Máxima</th>
                            <th style="text-align: center;">Mínima</th>
                            <th style="text-align: center;">Máxima</th>
                            <th style="text-align: center;">Mínima</th>
                            <th style="text-align: center;">Máxima</th>
                            <th colspan="2"></th>
                        </tr>
                        <c:forEach items="${configuracoes}" var="o">
                            <tr  style="text-align: center;">
                                <td><c:out value="${o.id}"/></td>
                                <td><c:out value="${o.nomePlantacao}"/></td>
                                <td><c:out value="${o.umidadeDoSoloMin}"/></td>
                                <td><c:out value="${o.umidadeDoSoloMax}"/></td>
                                <td><c:out value="${o.umidadeDoArMin}"/></td>
                                <td><c:out value="${o.umidadeDoArMax}"/></td>
                                <td><c:out value="${o.temperaturaMin}"/></td>
                                <td><c:out value="${o.temperaturaMax}"/></td>
                                <td><c:out value="${o.localizacao.idArduino}"/></td>
                                <td>
                                    <a href="ConfiguracaoServlet?acao=selecionar&id=${o.id}"><input type="button" class="btn btn-primary"  value="Selecionar"></a>
                                    <a href="ConfiguracaoServlet?acao=remover&id=${o.id}"><input type="button" class="btn btn-danger"  value="Excluir"></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>

            <div class="row" style="float: right; margin-right: 100px;">
                <a href="ConfiguracaoServlet?acao=novoRegistro"><input type="button" class="btn btn-info"  value="Novo"></a>
            </div>

        </div>

        <br/>

        <c:if test="${tamanhoLista >= 1}">
            <c:import url="rodape.jsp">
                <c:param name="bottom" value="auto"/>
            </c:import>
        </c:if>


        <c:if test="${tamanhoLista < 1}">
            <c:import url="rodape.jsp">
                <c:param name="bottom" value="0"/>
            </c:import>
        </c:if>
    </body>
</html>
