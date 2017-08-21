$(function () {
    $("#btn-buscar").click(function () {
        $("#tabla-resultados").show();
        $('#tabla-indicadores').replaceWith($('<tbody id="tabla-indicadores"></tbody>'));
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/dondeInvierto/indicadores/",
            dataType: "json",
            beforeSend: function () {
                console.log("Buscando información de indicadores...");
            },
            success: function (response) {
                $.each(response, function(index, element) {
                    $('#tabla-indicadores').append($('<tr><th scope="row">' + (index+1) +
                        '</th><td>' + element.nombre +
                        '</td><td>' + element.formula +
                        '</td></tr>'));
                });    
            }
        });
    });
});