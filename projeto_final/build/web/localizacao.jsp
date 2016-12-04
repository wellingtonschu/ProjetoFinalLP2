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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDge1f8EkFLUenbtOusmmKMJTAfMphZeug&libraries=weather,geometry,visualization,places,drawing&amp;sensor=false" type="text/javascript"></script>
        <script type="text/javascript" src="jquery-google-map/infobox.js"></script>
        <script type="text/javascript" src="jquery-google-map/markerclusterer.js"></script>
        <script type="text/javascript" src="jquery-google-map/jquery-google-map.js"></script>
        <title>Localização</title>
    </head>
    <body>
        <c:import url="topo.jsp">
            <c:param name="ativa_localizacao" value="active"/>
        </c:import>

        <div class="container" style="width: 600px">

            <h1 style="text-align: center;">Cadastro de Localização</h1>

            <form class="form-signin" method="POST" action="LocalServlet">

                <input id="txtId" type="hidden" name="id" value="${localizacao.id}"/>
                <label for="txtIdArduino" >ID Arduíno</label>
                <input value="${localizacao.idArduino}" type="text" id="txtIdArduino" 
                       name="idArduino" class="form-control" required autofocus/>

                <div class="row" style="width: 600px">
                    <div class="col-sm-12">
                        <h2 class="page-header" style="text-align: center;">Posição no mapa <small>(Clique para adicionar o ponto ou arraste para indicar a localização do imóvel)</small></h2>

                        <div class="box">

                            <div id="map-canvas" style="height: 400px"></div>

                            
                                <input value="${localizacao.latitude}" class="form-control" type="hidden" id="input-latitude" name="latitude">
                            

                           
                                
                                <input value="${localizacao.longitude}" class="form-control" type="hidden" id="input-longitude" name="longitude">
                            


                        </div>
                    </div>
                </div>

                <!--Ocultos latitude e longitude-->

                <!--termina parte oculta-->

                <br/>

                <div style="text-align: right;">
                    <c:if test="${localizacao.id == null}">
                        <button class="btn btn-success" type="submit" name="acao" value="inserir">
                            Salvar
                        </button>                        
                    </c:if>
                    <c:if test="${localizacao.id != null}">
                        <button class="btn btn-success" type="submit" name="acao" value="alterar">
                            alterar
                        </button>
                    </c:if>
                    <button class="btn btn-default" type="submit" name="acao" value="cancelar">Cancelar</button>
                </div>
            </form>
        </div>

        <!-- DEFINE A POSIÇÂO PADRÃO PARA O MAPA E OP PONTEIRO -->
        <script>
            var lat = -27.416569;
            var long = -49.601097;
        </script>

        <!-- AQUI TU FAZ A VERIFICAÇÃo EM JSP PRA VERIFICAR SE TEM DADOS GRAVADOS NO BANCO E EXUCUTA -->
        <c:if test="${localizacao.latitude != null}">
            <script>
                var lat = ${localizacao.latitude};
            </script>
            <c:if test="${localizacao.longitude != null}">
                <script>
                    var long = ${localizacao.longitude};
                </script>
            </c:if>
        </c:if>

        <script type="text/javascript">
            var map;
            var marker = new google.maps.Marker({
                draggable: true,
                map: map,
                anchorPoint: new google.maps.Point(lat, long)
            });
            var placeSearch, autocomplete;
            var componentForm = {
                street_number: 'short_name',
                route: 'long_name',
                locality: 'long_name',
                administrative_area_level_1: 'short_name',
                country: 'long_name',
                postal_code: 'short_name'
            };

            $(document).ready(function () {
                function initialize() {
                    var mapOptions = {
                        center: new google.maps.LatLng(lat, long),
                        zoom: 13,
                        scrollwheel: false,
                        mapTypeId: google.maps.MapTypeId.HYBRID
                    };
                    map = new google.maps.Map(document.getElementById('map-canvas'),
                            mapOptions);
                    google.maps.event.addListener(map, 'click', function (event) {
                        placeMarker(event.latLng);
                    });
                    var mev = {
                        stop: null,
                        latLng: new google.maps.LatLng(lat, long)
                    }

                    google.maps.event.trigger(map, 'click', mev);

                    var placemarker = true;
                    function placeMarker(location) {
                        if (placemarker) {
                            marker.setPosition(location);
                            $('#input-latitude').val(marker.position.lat());
                            $('#input-longitude').val(marker.position.lng());
                        } else {
                            marker = new google.maps.Marker({
                                position: location,
                                map: map,
                                draggable: true,
                                animation: google.maps.Animation.DROP
                            });
                            $('#input-latitude').val(marker.position.lat());
                            $('#input-longitude').val(marker.position.lng());
                            marker.addListener('dragend', function (event) {
                                $('#input-latitude').val(marker.position.lat());
                                $('#input-longitude').val(marker.position.lng());
                            });
                            marker.addListener('click', toggleBounce);
                            function toggleBounce() {
                                if (marker.getAnimation() !== null)
                                    marker.setAnimation(null);
                                else
                                    marker.setAnimation(google.maps.Animation.BOUNCE);
                            }
                            placemarker = true;
                        }
                    }

                    var infowindow = new google.maps.InfoWindow();


                    google.maps.event.addListener(marker, "mouseup", function (event) {
                        $('#input-latitude').val(this.position.lat());
                        $('#input-longitude').val(this.position.lng());
                    });

                    google.maps.event.addListener(autocomplete, 'place_changed', function () {
                        infowindow.close();
                        marker.setVisible(false);
                        var place = autocomplete.getPlace();
                        if (!place.geometry) {
                            return;
                        }

                        // If the place has a geometry, then present it on a map.
                        if (place.geometry.viewport) {
                            map.fitBounds(place.geometry.viewport);
                        } else {
                            map.setCenter(place.geometry.location);
                            map.setZoom(17);
                        }
                        marker.setIcon(/** @type {google.maps.Icon} */({
                            url: place.icon,
                            size: new google.maps.Size(71, 71),
                            origin: new google.maps.Point(0, 0),
                            anchor: new google.maps.Point(17, 34),
                            scaledSize: new google.maps.Size(35, 35)
                        }));
                        marker.setPosition(place.geometry.location);
                        marker.setVisible(true);

                        $('#input-latitude').val(place.geometry.location.lat());
                        $('#input-longitude').val(place.geometry.location.lng());

                        var address = '';
                        if (place.address_components) {
                            address = [
                                (place.address_components[0] && place.address_components[0].short_name || ''),
                                (place.address_components[1] && place.address_components[1].short_name || ''),
                                (place.address_components[2] && place.address_components[2].short_name || '')
                            ].join(' ');
                        }

                        infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
                        infowindow.open(map, marker);
                    });
                }


                if ($('#map-canvas').length != 0) {
                    google.maps.event.addDomListener(window, 'load', initialize);
                    geolocate();
                }
            });

            function geolocate() {
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(function (position) {
                        var geolocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                        var latitude = position.coords.latitude;
                        var longitude = position.coords.longitude;
                        document.getElementById("input-latitude").value = latitude;
                        document.getElementById("input-longitude").value = longitude;
                        map.setCenter(new google.maps.LatLng(position.coords.latitude, position.coords.longitude));
                        marker.setPosition(new google.maps.LatLng(position.coords.latitude, position.coords.longitude));
                    });
                }

            }
        </script>
        <br/>

        <c:import url="rodape.jsp">
            <c:param name="bottom" value="auto"/>
        </c:import>
    </body>
</html>
