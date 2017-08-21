$(function () {
    $("#btn-registro").click(function () {
        var data = {};
        data.nombre = $("#nombre").val();
        data.formula = $("#formula").val();
        console.log(JSON.stringify(data));

        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/dondeInvierto/indicadores/nuevo",
            dataType: "application/json",
            contentType: "application/json",
            data: JSON.stringify(data),
            beforeSend: function () {
                console.log("Enviando información del indicador...");
            },
            success: function (response) {
                console.log(response);
            },
            failure: function (errMsg) {
                alert(errMsg);
            }
        });
    });
});