function toggleCuentaPrompt() {
    $(".popup-bg").fadeToggle();
}

function toggleManualEntryPrompt() {
    toggleCuentaPrompt();
    $(".manual-entry").fadeToggle();
}

function toggleFileUploadPrompt() {
    toggleCuentaPrompt();
    $(".file-upload").fadeToggle();
}

function toggleFileUploadSuccess() {
    $("#uploadSuccess").fadeIn(200);
    $("#uploadSuccess").delay(5000).fadeOut(200);
}

function toggleFileUploadError() {
    $("#uploadError").fadeIn(200);
    $("#uploadError").delay(5000).fadeOut(200);
}

function toggleRegistrySuccess() {
    $("#registrySuccess").fadeIn(200);
    $("#registrySuccess").delay(5000).fadeOut(200);
}

function registryError() {
    $("#registryError").fadeIn(200);
    $("#registryError").delay(5000).fadeOut(200);
}

function uploadSuccess() {
  toggleFileUploadPrompt();
  toggleFileUploadSuccess();
}

function registrySuccess() {
  toggleManualEntryPrompt();
  toggleRegistrySuccess();
}

function toggleMetodologiaPrompt() {
    $(".popup-bg").fadeToggle();
}

function toggleMetodologiaEntryPrompt() {
    hideConds(50);
    toggleMetodologiaPrompt();
    $(".manual-entry").fadeToggle();
    $("#comboCond").val('-1');
}

function showCond(x) {
  switch (parseInt(x)) {
    case 1:
          hideConds(500);
          setTimeout( function() {
            $(".condicion1").delay(550).css('display', 'table');
          }, 750);
          break;
    case 2:
          hideConds(500);
          setTimeout( function() {
            $(".condicion2").delay(550).css('display', 'table');
          }, 750);
          break;
    case 3:
          hideConds(500);
          setTimeout( function() {
            $(".condicion3").delay(550).css('display', 'table');
          }, 750);
          break;
    case 4:
          hideConds(500);
          setTimeout( function() {
            $(".condicion4").delay(550).css('display', 'table');
          }, 750);
          break;
  }
}

function hideConds(x) {
  $(".condicion1").fadeOut(x);
  $(".condicion2").fadeOut(x);
  $(".condicion3").fadeOut(x);
  $(".condicion4").fadeOut(x);
}

$(function () {
    $("#btn-buscar-cuentas").click(function () {
        $("#tabla-resultados").show();
        $('#tabla-cuentas').replaceWith($('<tbody id="tabla-cuentas"></tbody>'));
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/dondeInvierto/cuentas/",
            dataType: "json",
            beforeSend: function () {
                console.log("[INFO] (AJAX) Buscando información de cuentas...");
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

$(function () {
    $("#btn-buscar-ind").click(function () {
        $("#tabla-resultados").show();
        $('#tabla-indicadores').replaceWith($('<tbody id="tabla-indicadores"></tbody>'));
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/dondeInvierto/indicadores/",
            dataType: "json",
            beforeSend: function () {
                console.log("[INFO] (AJAX) Buscando información de indicadores...");
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

$(function () {
    $("#btn-registro-ind").click(function () {
        var data = {};
        data.nombre = $("#nombre").val();
        data.formula = $("#formula").val();

        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/dondeInvierto/indicadores/nuevo",
            dataType: "text",
            contentType: "application/json",
            data: JSON.stringify(data),
            beforeSend: function () {
                console.log("[INFO] (AJAX) Enviando información del indicador...");
            },
            success: function () {
                console.log("Success!");
                toggleRegistrySuccess();
            },
            error: function (jqXHR, exception) {
                var msg = '';
                if (jqXHR.status === 0) {
                    msg = 'Not connect.\n Verify Network.';
                } else if (jqXHR.status == 400) {
                    msg = 'Bad request. [400]';
                } else if (jqXHR.status == 404) {
                    msg = 'Requested page not found. [404].';
                } else if (jqXHR.status == 500) {
                    msg = 'Internal Server Error [500].';
                } else if (exception === 'parsererror') {
                    msg = 'Requested JSON parse failed.\n' + jqXHR.responseText + '.\n' + jqXHR.status + '.';
                } else if (exception === 'timeout') {
                    msg = 'Time out error.';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted.';
                } else {
                    msg = 'Uncaught Error.\n' + jqXHR.responseText + '.\n' + jqXHR.status + '.';
                }
                console.log(msg);
                registryError();
            }
        });
    });
});

$(function () {
    $("#file-upload-btn").click(function () {
        if (document.getElementById("archivo-cuentas").files.length == 0) {
            console.log("[INFO] (AJAX) No hay archivo seleccionado.");
        } else {
            var formData = new FormData();
            jQuery.each(jQuery('#archivo-cuentas')[0].files, function(i, file) {
                formData.append('file-'+i, file);
            });

            $.ajax({
                method: 'POST',
                type: 'POST',
                url: "http://localhost:8080/dondeInvierto/cuentas/subirArchivo",
                dataType: "text",
                contentType: false,
                processData: false,
                data: formData,
                beforeSend: function () {
                    console.log("[INFO] (AJAX) Enviando archivo de cuentas...");
                },
                success: function () {
                    console.log("File received!");
                    toggleFileUploadPrompt();
                    toggleFileUploadSuccess();
                },
                error: function (jqXHR, exception) {
                    var msg = '';
                    if (jqXHR.status === 0) {
                        msg = 'Not connect.\n Verify Network.';
                    } else if (jqXHR.status == 400) {
                        msg = 'Bad request. [400]';
                    } else if (jqXHR.status == 404) {
                        msg = 'Requested page not found. [404].';
                    } else if (jqXHR.status == 500) {
                        msg = 'Internal Server Error [500].';
                    } else if (exception === 'parsererror') {
                        msg = 'Requested JSON parse failed.\n' + jqXHR.responseText + '.\n' + jqXHR.status + '.';
                    } else if (exception === 'timeout') {
                        msg = 'Time out error.';
                    } else if (exception === 'abort') {
                        msg = 'Ajax request aborted.';
                    } else {
                        msg = 'Uncaught Error.\n' + jqXHR.responseText + '.\n' + jqXHR.status + '.';
                    }
                    console.log(msg);
                    toggleFileUploadPrompt();
                    toggleFileUploadError();
                }
            });
        }
    });
});