<%-- 
    Document   : teste
    Created on : 06/12/2016, 00:00:55
    Author     : Alex Manoel Coelho <alexma_coelho@hotmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tr>
    <td><c:out value="${ultimaLeitura.localizacao.latitude}; ${ultimaLeitura.localizacao.longitude}"/></td>
    <td><fmt:formatDate value="${ultimaLeitura.data}" pattern="dd/MM/yyyy"/></td>
<td><fmt:formatDate value="${ultimaLeitura.horario}" pattern="HH:mm"/></td>
<td><c:out value="${ultimaLeitura.situacao.situacao}"/></td>
    </tr>
