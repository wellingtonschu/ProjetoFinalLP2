<%-- 
    Document   : topo
    Created on : 16/08/2016, 08:34:14
    Author     : Alex
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<footer class="section section-info" style="background-color: #1253ac">
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <h1>Irrigação</h1>
            </div>
            <div class="col-sm-6">
                <div class="row">
                    <div class="col-md-12 hidden-xs text-right">
                        <a href="#"><img src="img\irrigacao-logo.png" width="120px"></a>
                    </div>
                </div>
            </div>
        </div>
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <c:if test="${logado != null}">
                        <ul class="nav nav-pills">                          
                           
                            <li class="${param.ativa_index}">
                                <a href="LogServlet">Home</a>
                            </li>                            
                            
                            <li class="${param.ativa_localizacao}">
                                <a href="LocalServlet">Localização</a>
                            </li>
                            <li class="${param.ativa_sensor}">
                                <a href="SensorServlet">Sensores</a>
                            </li>
                            <li class="${param.ativa_configuracao}">
                                <a href="ConfiguracaoServlet">Configuração</a>
                            </li>
                            <ul class="nav nav-pills navbar-right">                                
                            <li>
                                <a href="LogoutServlet">Sair</a>
                            </li>                        
                            </ul>
                        
                        </ul>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>
