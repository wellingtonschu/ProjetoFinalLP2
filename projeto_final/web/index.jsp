<%-- 
    Document   : index
    Created on : 01/11/2016, 08:24:24
    Author     : Alex
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String logado = (String) session.getAttribute("logado");
%>

<c:if test="${logado == null}">
    <c:redirect url="login.jsp"/>
</c:if>

<c:if test="${leituras == null}">
    <c:redirect url="LogServlet"/>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style type="text/css">
            @import "css/jquery.dataTables.css";
        </style>
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script src="js/jquery.js"></script>
        <script src="js/jquery.dataTables.js"></script>
        <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="css\style.css" rel="stylesheet" type="text/css">
        <title>Histórico de Irrigações</title>

        <script>
            $(document).ready(function ()
            {
                $('#log').dataTable({
                    "oLanguage": {
                        "sLengthMenu": "Mostrar _MENU_ registros por página",
                        "sZeroRecords": "Nenhum registro encontrado",
                        "sInfo": "Mostrando _START_ / _END_ de _TOTAL_ registro(s)",
                        "sInfoEmpty": "Mostrando 0 / 0 de 0 registros",
                        "sInfoFiltered": "(filtrado de _MAX_ registros)",
                        "sSearch": "Pesquisar: ",
                        "oPaginate": {
                            "sFirst": "Início",
                            "sPrevious": "Anterior",
                            "sNext": "Próximo",
                            "sLast": "Último"
                        }
                    },
                    "aaSorting": [[0, 'desc']],
                    "aoColumnDefs": [
                        {"sType": "num-html", "aTargets": [0]}
                    ]
                });
            });


        </script>

        <script type="text/javascript">

            function Atualizar() {
               /* $.ajax({
                    url: 'PegaUltimoServlet',
                    method: 'POST',
                    success: function (data) {
                        $('#log tbody').prepend(data);
                    }
                });*/
                window.location.reload();
            }
            

        </script>



    </head>
    <body onload="setInterval('Atualizar()', 60000)">

        <c:import url="topo.jsp">
            <c:param name="ativa_index" value="active"/>
        </c:import>

        <div class="row">
            <div class="col-md-12">
                <h3 style="text-align: center">Histórico de Irrigações</h3>
                <c:if test="${leituras==null}">
                    <h2>Histórico vazio</h2>
                </c:if>
            </div>
        </div>

        <c:if test="${leituras!=null}">
            <div  id="conteudo" class="row">
                <div class="col-md-12">
                    <div class="container">
                        <table id="log" class="table table-hover">
                            <thead>
                            <th style="text-align: center; width: 30%">IdArduino</th>
                            <th style="text-align: center; width: 20%">Data</th>
                            <th style="text-align: center; width: 20%">Horário</th>
                            <th style="text-align: center; width: 30%">Situação</th>
                            </thead>
                            <tbody>
                            <div id="carregaDados">

                            </div>                            
                                <c:forEach items="${leituras}" var="o">
                                <tr style="text-align: center">
                                    <td><c:out value="${o.localizacao.idArduino}"/></td>
                                    <td><fmt:formatDate value="${o.data}" pattern="dd/MM/yyyy"/></td>
                                    <td><fmt:formatDate value="${o.horario}" pattern="HH:mm"/></td>
                                    <td><c:out value="${o.situacao.situacao}"/></td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
            <br/>
        </c:if>

        <c:if test="${tamanhoLista >= 3}">
            <c:import url="rodape.jsp">
                <c:param name="bottom" value="auto"/>
            </c:import>
        </c:if>


        <c:if test="${tamanhoLista < 3}">
            <c:import url="rodape.jsp">
                <c:param name="bottom" value="0"/>
            </c:import>
        </c:if>

    </body>
</html>
