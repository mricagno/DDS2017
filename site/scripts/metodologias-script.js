$(document).ready(
    function () {
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/dondeInvierto/indicadores/",
            dataType: "json",
            beforeSend: function () {
                console
                    .log("[INFO] (AJAX) Buscando información de indicadores...");
            },
            success: function (response) {
                var selectOptionsHtml;
                $.each(response, function (index,
                                           element) {
                    selectOptionsHtml = selectOptionsHtml
                        + '<option>'
                        + element.nombre
                        + '</option>';
                });
                $('#indicadorCondicion')
                    .html(selectOptionsHtml)
                    .selectpicker('refresh');
            }
        });
    });