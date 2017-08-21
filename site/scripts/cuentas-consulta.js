$(function () {
    $("#btn-buscar").click(function () {
        $("#tabla-resultados").show();
        $('#tabla-cuentas').replaceWith($('<tbody id="tabla-cuentas"></tbody>'));
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/dondeInvierto/cuentas/",
            dataType: "json",
            beforeSend: function () {
                console.log("Buscando información de cuentas...");
            },
            success: function (response) {
                $.each(response, function(index, element) {
                    $('#tabla-cuentas').append($('<tr><th scope="row">' + (index+1) +
                        '</th><td>' + element.empresa +
                        '</td><td>' + new Date(
                            element.periodo.substring(0,4) + "-" +
                            element.periodo.substring(4,6) + "-" +
                            element.periodo.substring(6,8)).toUTCString().substring(5,16) +
                        '</td><td>' + element.tipo + 
                        '</td><td class="cuentaValor">' + element.valor +
                        '</td></tr>'));
                });    
            }
        });
    });
});