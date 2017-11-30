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

function uploadSuccess() {
    toggleFileUploadPrompt();
    toggleFileUploadSuccess();
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

function registrySuccess() {
    toggleManualEntryPrompt();
    toggleRegistrySuccess();
}

function toggleLoginSuccess() {
    $("#loginSuccess").fadeIn(200);
    $("#loginSuccess").delay(5000).fadeOut(200);
}

function toggleLoginError() {
    $("#loginError").fadeIn(200);
    $("#loginError").delay(5000).fadeOut(200);
}

function loginSuccess() {
    toggleLoginSuccess()
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
            setTimeout(function () {
                $(".condicion1").delay(550).css('display', 'table');
            }, 750);
            break;
        case 2:
            hideConds(500);
            setTimeout(function () {
                $(".condicion2").delay(550).css('display', 'table');
            }, 750);
            break;
        case 3:
            hideConds(500);
            setTimeout(function () {
                $(".condicion3").delay(550).css('display', 'table');
            }, 750);
            break;
        case 4:
            hideConds(500);
            setTimeout(function () {
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

function hideLogin() {
    $("#login-box").hide();
}

function showNav() {
    console.log(document.getElementById("dropdown1"));
    console.log($(document.getElementById("dropdown1")));
    $(document.getElementById("dropdown1")).css("display", "block");
}

function hideNav() {
    console.log(document.getElementById("dropdown1"));
    console.log($(document.getElementById("dropdown1")));
    $(document.getElementById("dropdown1")).hide();
}

function validateLogin() {
    var data = {};
    data.usuario = $("#usuario").val();
    data.password = $("#password").val();
    $
        .ajax({
            type: 'POST',
            url: "http://localhost:8080/dondeInvierto/login/auth",
            dataType: "text",
            contentType: "application/json",
            data: JSON.stringify(data),
            beforeSend: function () {
                console
                    .log("[INFO] (AJAX) Buscando información de usuarios...");
            },
            success: function (response) {
                console.log("Success!");
                toggleLoginSuccess();
                showNav();
                hideLogin();
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
                    msg = 'Requested JSON parse failed.\n'
                        + jqXHR.responseText
                        + '.\n'
                        + jqXHR.status
                        + '.';
                } else if (exception === 'timeout') {
                    msg = 'Time out error.';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted.';
                } else {
                    msg = 'Uncaught Error.\n'
                        + jqXHR.responseText
                        + '.\n' + jqXHR.status
                        + '.';
                }
                console.log(msg);
                toggleLoginError();
            }
        });
}

$(function () {
    $('#login-box').keypress(function (e) {
        if (e.which == 13) {
            validateLogin();
        }
    });
});

$(function () {
    $("#btn-login-usr").click(
        function () {
            validateLogin();
        });
});

$(function () {
    $("#btn-buscar-cuentas")
        .click(
            function () {
                $("#tabla-resultados").show();
                $('#tabla-cuentas').replaceWith(
                    $('<tbody id="tabla-cuentas"></tbody>'));
                $
                    .ajax({
                        type: 'GET',
                        url: "http://localhost:8080/dondeInvierto/cuentas/",
                        dataType: "json",
                        beforeSend: function () {
                            console
                                .log("[INFO] (AJAX) Buscando información de cuentas...");
                        },
                        success: function (response) {
                            $
                                .each(
                                    response,
                                    function (index, element) {
                                        $('#tabla-cuentas')
                                            .append(
                                                $('<tr><th scope="row">'
                                                    + (index + 1)
                                                    + '</th><td>'
                                                    + element.empresa
                                                    + '</td><td>'
                                                    + new Date(
                                                        element.periodo
                                                            .substring(
                                                                0,
                                                                4)
                                                        + "-"
                                                        + element.periodo
                                                            .substring(
                                                                4,
                                                                6)
                                                        + "-"
                                                        + element.periodo
                                                            .substring(
                                                                6,
                                                                8))
                                                        .toUTCString()
                                                        .substring(
                                                            5,
                                                            16)
                                                    + '</td><td>'
                                                    + element.tipo
                                                    + '</td><td class="cuentaValor">'
                                                    + element.valor
                                                    + '</td></tr>'));
                                    });
                        }
                    });
            });
});

$(function () {
    $("#btn-buscar-ind")
        .click(
            function () {
                $("#tabla-resultados").show();
                $('#tabla-indicadores').replaceWith(
                    $('<tbody id="tabla-indicadores"></tbody>'));
                $
                    .ajax({
                        type: 'GET',
                        url: "http://localhost:8080/dondeInvierto/indicadores/",
                        dataType: "json",
                        beforeSend: function () {
                            console
                                .log("[INFO] (AJAX) Buscando información de indicadores...");
                        },
                        success: function (response) {
                            $.each(response, function (index,
                                                       element) {
                                $('#tabla-indicadores').append(
                                    $('<tr><th scope="row">'
                                        + (index + 1)
                                        + '</th><td>'
                                        + element.nombre
                                        + '</td><td>'
                                        + element.formula
                                        + '</td></tr>'));
                            });
                        }
                    });
            });
});

$(function () {
    $("#btn-registro-ind")
        .click(
            function () {
                var data = {};
                data.nombre = $("#nombre").val();
                data.formula = $("#formula").val();

                $
                    .ajax({
                        type: 'POST',
                        url: "http://localhost:8080/dondeInvierto/indicadores/nuevo",
                        dataType: "text",
                        contentType: "application/json",
                        data: JSON.stringify(data),
                        beforeSend: function () {
                            console
                                .log("[INFO] (AJAX) Enviando información del indicador...");
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
                                msg = 'Requested JSON parse failed.\n'
                                    + jqXHR.responseText
                                    + '.\n'
                                    + jqXHR.status
                                    + '.';
                            } else if (exception === 'timeout') {
                                msg = 'Time out error.';
                            } else if (exception === 'abort') {
                                msg = 'Ajax request aborted.';
                            } else {
                                msg = 'Uncaught Error.\n'
                                    + jqXHR.responseText
                                    + '.\n' + jqXHR.status
                                    + '.';
                            }
                            console.log(msg);
                            registryError();
                        }
                    });
            });
});

$(function () {
    $("#btn-borrar-ind")
        .click(
            function () {
                var nombre = $("#nombre").val();
                $
                    .ajax({
                        type: 'DELETE',
                        url: "http://localhost:8080/dondeInvierto/indicadores/borrar"
                        + nombre,
                        contentType: "application/json",
                        beforeSend: function () {
                            console
                                .log("[INFO] (AJAX) Enviando información del indicador...");
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
                                msg = 'Requested JSON parse failed.\n'
                                    + jqXHR.responseText
                                    + '.\n'
                                    + jqXHR.status
                                    + '.';
                            } else if (exception === 'timeout') {
                                msg = 'Time out error.';
                            } else if (exception === 'abort') {
                                msg = 'Ajax request aborted.';
                            } else {
                                msg = 'Uncaught Error.\n'
                                    + jqXHR.responseText
                                    + '.\n' + jqXHR.status
                                    + '.';
                            }
                            console.log(msg);
                            registryError();
                        }
                    });
            });
});

$(function () {
    $("#btn-buscar-meto")
        .click(
            function () {
                $("#tabla-resultados").show();
                $('#tabla-metodologias').replaceWith(
                    $('<tbody id="tabla-metodologias"></tbody>'));
                $
                    .ajax({
                        type: 'GET',
                        url: "http://localhost:8080/dondeInvierto/metodologias",
                        dataType: "json",
                        beforeSend: function () {
                            console
                                .log("[INFO] (AJAX) Buscando información de metodologias...");
                        },
                        success: function (response) {
                            $.each(response, function (index, element) {
                                var cantCond = Object.keys(element.condiciones).length + 1;
                                $('#tabla-metodologias').append(
                                    $('<tr><th scope="row" rowspan="'
                                        + cantCond
                                        + '">'
                                        + (index + 1)
                                        + '</th><td rowspan="'
                                        + cantCond
                                        + '">'
                                        + element.nombre
                                        + '</td>'));
                                $.each(element.condiciones, function (index, child) {
                                    if (child.tipo == "Filtro") {
                                        $('#tabla-metodologias').append(
                                            $('<tr><td>'
                                                + child.tipo
                                                + '</td><td>'
                                                + child.nombre
                                                + '</td><td>'
                                                + child.indicador
                                                + '</td><td>'
                                                + child.filtro
                                                + '</td>'));
                                    } else {
                                        $('#tabla-metodologias').append(
                                            $('<tr><td>'
                                                + child.tipo
                                                + '</td><td>'
                                                + child.nombre
                                                + '</td><td>'
                                                + child.indicador
                                                + '</td><td>'
                                                + child.orden
                                                + '</td>'));
                                    }
                                });
                                $('#tabla-metodologias').append(
                                    '</tr>');
                            });
                        }
                    });
            });
});

$(function () {
    $('#tipoCondicion').on('change', function () {
        if ($(this).find("option:selected").val() === 'Filtro') {
            $('#criterioCondicion')
                .html(
                    '<option>filtrarAntiguedadMayor</option>')
                .selectpicker('refresh');
            $('#criterioCondicion')
                .prop('disabled', false);
            $('#criterioCondicion')
                .selectpicker('refresh');
        } else {
            $('#criterioCondicion')
                .html(
                    '<option>Ascendente</option>'
                    + '<option>Descendente</option>')
                .selectpicker('refresh');
            $('#criterioCondicion')
                .prop('disabled', false);
            $('#criterioCondicion')
                .selectpicker('refresh');
        }
    });
});

$(function () {
        $("#btn-agregar-condicion")
            .click(
                function () {
                    $('#tabla-metodologias').append(
                        $('<tr><td>'
                            + $('#tipoCondicion').find("option:selected").text()
                            + '</td><td>'
                            + $('#nombreCondicion').val()
                            + '</td><td>'
                            + $('#indicadorCondicion').find("option:selected").text()
                            + '</td><td>'
                            + $('#criterioCondicion').find("option:selected").text()
                            + '</td>'));
                }
            );
    }
);

$(function () {
    $("#btn-registro-meto")
        .click(
            function () {
                var data = {};
                data.nombre = $("#nombreMetodologia").val();
                data.condiciones.tipo = $("#tipoCondicion").find("option:selected").text().toLowerCase();


                $.ajax({
                    type: 'POST',
                    url: "http://localhost:8080/dondeInvierto/metodologias/nueva",
                    dataType: "text",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    beforeSend: function () {
                        console
                            .log("[INFO] (AJAX) Enviando información del indicador...");
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
                            msg = 'Requested JSON parse failed.\n'
                                + jqXHR.responseText
                                + '.\n'
                                + jqXHR.status
                                + '.';
                        } else if (exception === 'timeout') {
                            msg = 'Time out error.';
                        } else if (exception === 'abort') {
                            msg = 'Ajax request aborted.';
                        } else {
                            msg = 'Uncaught Error.\n'
                                + jqXHR.responseText
                                + '.\n' + jqXHR.status
                                + '.';
                        }
                        console.log(msg);
                        registryError();
                    }
                });
            });
});

$(document).ready(function () {
    if (window.location.pathname == "/index.html") {
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/dondeInvierto/login/logged",
            dataType: "json",
            beforeSend: function () {
                console
                    .log("[INFO] (AJAX) Buscando información de usuario logueado...");
            },
            success: function (response) {
                if (response.usuario === "") {
                    console.log("[INFO] (AJAX) No hay usuario logueado.");
                } else {
                    console.log("[INFO] (AJAX) El usuario sigue logueado.");
                    hideLogin();
                    showNav();
                }
            }
        });
    }
});


$(function btnLogin() {
    $("#btn-cerrar-sesion")
        .click(
            function () {
                $
                    .ajax({
                        type: 'POST',
                        url: "http://localhost:8080/dondeInvierto/login/out",
                        dataType: "text",
                        contentType: "application/json",
                        beforeSend: function () {
                            console
                                .log("[INFO] (AJAX) Buscando información de usuario logueado...");
                        },
                        success: function (response) {
                            console.log("Success!");
                            document.location.href = "index.html";
                            $(document).ready(hideNav());
                        },
                    });
            });
});