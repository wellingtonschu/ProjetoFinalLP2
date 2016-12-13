<%-- 
    Document   : configuracao
    Created on : 03/12/2016, 16:28:45
    Author     : Alex Manoel Coelho <alexma_coelho@hotmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>       
        <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="css\style.css" rel="stylesheet" type="text/css">
        <title>Configuração</title>

        <script src="js/jquery.maskedinput.min.js" type="text/javascript"></script>
        <script src="js/jquery.maskMoney.js" type="text/javascript"></script>


        <script type="text/javascript">
            $(function () {
                $("#txtUmidadeDoSoloMin").maskMoney();
                $("#txtUmidadeDoSoloMax")maskMoney();
                $("#txtUmidadeDoArMin").maskMoney();
                $("#txtUmidadeDoArMax").maskMoney();
                $("#txtTemperaturaMin").maskMoney();
                $("#txtTemperaturaMax").maskMoney();
            })
        </script>
        <script type="text/javascript">
            $(function () {
                $("#txtUmidadeDoSoloMin").maskMoney({symbol: ' ',
                    thousands: '.', decimal: '.', symbolStay: true, showSymbol: false});
                $("#txtUmidadeDoSoloMax").maskMoney({symbol: ' ',
                    thousands: '.', decimal: '.', symbolStay: true, showSymbol: false});
                $("#txtUmidadeDoArMin").maskMoney({symbol: ' ',
                    thousands: '.', decimal: '.', symbolStay: true, showSymbol: false});
                $("#txtUmidadeDoArMax").maskMoney({symbol: ' ',
                    thousands: '.', decimal: '.', symbolStay: true, showSymbol: false});
                $("#txtTemperaturaMin").maskMoney({symbol: ' ',
                    thousands: '.', decimal: '.', symbolStay: true, showSymbol: false});
                $("#txtTemperaturaMax").maskMoney({symbol: ' ',
                    thousands: '.', decimal: '.', symbolStay: true, showSymbol: false});
            })
        </script>

    </head>
    <body>

        <c:import url="topo.jsp">
            <c:param name="ativa_configuracao" value="active"/>
        </c:import>
        <div class="row">
            <div class="col-md-12">
                <h1 style="text-align: center;">Cadastro de Configuração</h1>
            </div>
        </div>

        <br/>

        <div class="container" style="width: 800px;">

            <form class="form-signin" method="POST" action="ConfiguracaoServlet" name="form1">

                <input type="hidden" id="txtId" name="id" value="${configuracao.id}">

                <div style="width: 800px; margin: 0 auto;">

                    <div class="row">
                        <div class="col-md-5">
                            <label for="txtNomePlantacao">Plantação</label>
                            <input type="text" id="txtNomePlantacao" name="nomePlantacao" class="form-control" value="${configuracao.nomePlantacao}"/>
                        </div>
                        <div class="col-md-7">
                            <label for="txt">Localização</label>
                            <select class="form-control" id="comboLocalizacao" size="1" name="localizacao">
                                <option value=""></option>
                                <c:forEach items="${localizacoes}" var="val"> 
                                    <option value="${val.id}" ${configuracao.localizacao.id == val.id ? 'selected' : ' '}><c:out value="${val.idArduino}" ></c:out></option>   
                                </c:forEach> 
                            </select>
                        </div>
                    </div>

                    <div class="row">                        
                        <div class="col-md-6" style="color: #551111; text-align: center;">
                            <h2 >Umidade do Solo (KPa)</h2>
                            <div class="col-md-6">
                                <label for="txtUmidadeDoSoloMin">Mínima</label>
                                <input tabindex="1" class="form-control" id="txtUmidadeDoSoloMin" name="umidadeDoSoloMin" value="${configuracao.umidadeDoSoloMin}" maxlength="5"/>
                            </div>
                            <div class="col-md-6">
                                <label for="txtUmidadeDoSoloMax">Máxima</label>
                                <input type="text" class="form-control" id="txtUmidadeDoSoloMax" name="umidadeDoSoloMax" value="${configuracao.umidadeDoSoloMax}" maxlength="5"/>
                            </div>
                        </div>

                        <div class="col-md-6" style="color: #006ac4;"> 							 
                            <h2 style="text-align: center;">Umidade do Ar (%)</h2>
                            <div class="col-md-6">
                                <label for="txtUmidadeDoArMin">Mínima</label>
                                <input type="text" class="form-control" id="txtUmidadeDoArMin" name="umidadeDoArMin" value="${configuracao.umidadeDoArMin}" maxlength="5"/>
                            </div>
                            <div class="col-md-6">
                                <label for="txtUmidadeDoArMax">Máxima</label>
                                <input type="text" class="form-control" id="txtUmidadeDoArMax" name="umidadeDoArMax" value="${configuracao.umidadeDoArMax}" maxlength="5"/>
                            </div>
                        </div>

                    </div><!--::: ROW :: -->

                    <div class="row">
                        <div class="container" style="width: 400px; text-align: center; color: #c90d0d;">  
                            <div class="col-md-12">
                                <h2>Temperatura (ºC)</h2>
                                <div class="col-md-6">
                                    <label for="txtTemperaturaMin">Mínima</label>
                                    <input type="text" class="form-control" id="txtTemperaturaMin" name="temperaturaMin" value="${configuracao.temperaturaMin}" maxlength="5"/>
                                </div>
                                <div class="col-md-6">
                                    <label for="txtTemperaturaMax">Máxima</label>
                                    <input type="text" class="form-control" id="txtTemperaturaMax" name="temperaturaMax" value="${configuracao.temperaturaMax}" maxlength="5"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <br/>

                </div>

                <br/>

                <div style="text-align: right;">
                    <c:if test="${configuracao.id == null}">
                        <button class="btn btn-success" type="submit" name="acao" value="inserir">
                            Salvar
                        </button>                        
                    </c:if>
                    <c:if test="${configuracao.id != null}">
                        <button class="btn btn-success" type="submit" name="acao" value="alterar">
                            Alterar
                        </button>
                    </c:if>
                    <button class="btn btn-default" type="submit" name="acao" value="cancelar">Cancelar</button>
                </div>
            </form>

            <br/>

        </div>
        <div id="info"></div>
        <c:import url="rodape.jsp">
            <c:param name="bottom" value="auto"/>
        </c:import>

    </body>
</html>
