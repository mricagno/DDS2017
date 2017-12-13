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
		setTimeout(function() {
			$(".condicion1").delay(550).css('display', 'table');
		}, 750);
		break;
		case 2:
		hideConds(500);
		setTimeout(function() {
			$(".condicion2").delay(550).css('display', 'table');
		}, 750);
		break;
		case 3:
		hideConds(500);
		setTimeout(function() {
			$(".condicion3").delay(550).css('display', 'table');
		}, 750);
		break;
		case 4:
		hideConds(500);
		setTimeout(function() {
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
	$(document.getElementById("dropdown1")).css("display", "block");
	$(document.getElementById("btn-cerrar-sesion")).css("display", "block");
	$(document.getElementById("btn-cerrar-sesion")).css("float", "right");
}

function hideNav() {
	$(document.getElementById("dropdown1")).hide();
	$(document.getElementById("btn-cerrar-sesion")).hide();
}

function validateLogin() {
	var data = {};
	data.usuario = $("#usuario").val();
	data.password = $("#password").val();
	$
	.ajax({
		type : 'POST',
		url : "http://localhost:8080/dondeInvierto/resource/login/auth",
		dataType : "text",
		contentType : "application/json",
		data : JSON.stringify(data),
		beforeSend : function() {
			console
			.log("[INFO] (AJAX) Buscando información de usuarios...");
		},
		success : function(response) {
			console.log("Success!");
			toggleLoginSuccess();
			showNav();
			hideLogin();
		},
		error : function(jqXHR, exception) {
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

$(function(){
	$('#login-box').keypress(function(e){
		if(e.which == 13) {
			validateLogin();
		}
	});
});

$(function() {
	$("#btn-login-usr").click(
		function() {
			validateLogin();
		});
});

$(function() {
	$("#btn-buscar-cuentas")
	.click(
		function() {
			$("#tabla-resultados").show();
			$('#tabla-cuentas').replaceWith(
				$('<tbody id="tabla-cuentas"></tbody>'));
			$
			.ajax({
				type : 'GET',
				url : "http://localhost:8080/dondeInvierto/resource/cuentas/",
				dataType : "json",
				beforeSend : function() {
					console
					.log("[INFO] (AJAX) Buscando información de cuentas...");
				},
				success : function(response) {
					$
					.each(
						response,
						function(index, element) {
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

$(function() {
	$("#btn-buscar-ind")
	.click(
		function() {
			$("#tabla-resultados").show();
			$('#tabla-indicadores').replaceWith(
				$('<tbody id="tabla-indicadores"></tbody>'));
			$
			.ajax({
				type : 'GET',
				url : "http://localhost:8080/dondeInvierto/resource/indicadores/",
				dataType : "json",
				beforeSend : function() {
					console
					.log("[INFO] (AJAX) Buscando información de indicadores...");
				},
				success : function(response) {
					$.each(response, function(index,
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

$(function() {
	$("#btn-registro-ind")
	.click(
		function() {
			var data = {};
			data.nombre = $("#nombre").val();
			data.formula = $("#formula").val();

			$
			.ajax({
				type : 'POST',
				url : "http://localhost:8080/dondeInvierto/resource/indicadores/nuevo",
				dataType : "text",
				contentType : "application/json",
				data : JSON.stringify(data),
				beforeSend : function() {
					console
					.log("[INFO] (AJAX) Enviando información del indicador...");
				},
				success : function() {
					console.log("Success!");
					toggleRegistrySuccess();
				},
				error : function(jqXHR, exception) {
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
					console.log(jqXHR.responseText);
					registryError();
				}
			});
		});
});

$(function() {
	$("#btn-borrar-ind")
	.click(
		function() {
			var nombre = $("#nombre").val();
			$
			.ajax({
				type : 'DELETE',
				url : "http://localhost:8080/dondeInvierto/resource/indicadores/borrar/"
				+ nombre,
				contentType : "application/json",
				beforeSend : function() {
					console
					.log("[INFO] (AJAX) Enviando información del indicador...");
				},
				success : function() {
					console.log("Success!");
					toggleRegistrySuccess();
				},
				error : function(jqXHR, exception) {
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

$(function() {
	$("#btn-buscar-meto")
	.click(
		function() {
			$("#tabla-resultados").show();
			$('#tabla-metodologias').replaceWith(
				$('<tbody id="tabla-metodologias"></tbody>'));

			$
			.ajax({
				type : 'GET',
				url : "http://localhost:8080/dondeInvierto/resource/metodologias",
				dataType : "json",
				beforeSend : function() {
					console
					.log("[INFO] (AJAX) Buscando información de metodologias...");
				},
				success : function(response) {
					$.each(response, function(index, element) {
						var cantCond = Object.keys(element.condiciones).length;
						var html = '';

						html += '<tr><td rowspan="' + cantCond + '">' + (index+1) + '</td>'
						html += '<td rowspan="'	+ cantCond + '">' + element.nombre + '</td>';

						$.each(element.condiciones, function(index, child) {
							if(child.tipo == "Filtro") {
								html += '<td>' + child.tipo + '</td>';
								html += '<td>' + child.nombre + '</td>';
								html += '<td>' + child.indicador + '</td>';
								html += '<td>' + child.filtro + '</td>';
								html += '</tr>'
							} else {
								html += '<td>' + child.tipo + '</td>';
								html += '<td>' + child.nombre + '</td>';
								html += '<td>' + child.indicador + '</td>';
								html += '<td>' + child.orden + '</td>'
								html += '</tr>';
							}});
						
						$('#tabla-metodologias').append(html);
					});
				}
			});
		});
});

$(function() {
	$('#tipoCondicion').on('change', function(){
		if ($(this).find("option:selected").val() === 'Filtro') {
			$('#criterioCondicion')
			.html(
				'<option>&lt;</option>' +
				'<option>&gt;</option>' +
				'<option>&lt;=</option>' +
				'<option>&gt;=</option>' +
				'<option>&lt;&gt;</option>' +
				'<option>=</option>')
			.selectpicker('refresh');
			$('#criterioCondicion')
			.prop('disabled', false);
			$('#criterioCondicion')
			.selectpicker('refresh');
			$('#indicadorCondicion')
			.append(
				'<option value="antiguedad">Antig&uuml;edad</option>');
			$('#indicadorCondicion')
			.selectpicker('refresh');
			$('#valorCondicion').css('display', 'block');
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
			$('#indicadorCondicion')
			.find('[value=antiguedad]')
			.remove();
			$('#indicadorCondicion')
			.selectpicker('refresh');
			$('#valorCondicion').css('display', 'none');
		}
	});
});

$(function() {
	$("#btn-agregar-condicion")
	.click(
		function() {
			var html = '';

			html += '<tr><td>';
			html += $('#tipoCondicion').find("option:selected").text();
			html += '</td><td>';
			html += $('#nombreCondicion').val();
			html += '</td><td>';
			html += $('#indicadorCondicion').find("option:selected").text();
			html += '</td><td>';
			html += $('#criterioCondicion').find("option:selected").text();
			html += '</td><td>';
			if ($('#tipoCondicion').find("option:selected").text() === "Filtro") {
				html += $('#valorCondicion').val();
			}
			html += '</td>';

			$('#tabla-metodologias').append(html);

			$('#tipoCondicion').selectpicker('val', '');
			$('#nombreCondicion').val('');
			$('#indicadorCondicion').selectpicker('val', '');
			$('#criterioCondicion').selectpicker('val', '');
			$('#valorCondicion').val('');
			$('#valorCondicion').css('display', 'none');
		});
});

$(function() {
	$("#btn-registro-meto")
	.click(
		function() {
			var data = {};
			data.nombre = $("#nombreMetodologia").val();
			data.condiciones = [];

			var rows = $('#tabla-metodologias').find('tr');
			for (var i = 0; i < rows.length; i++) {
				var cols = $(rows[i]).find('td');
				var condicion = {};

				condicion.tipo = cols[0].innerText;
				condicion.nombre = cols[1].innerText;
				if (cols[2].innerText === "Antigüedad") {
					condicion.indicador = 'Indicador Vacio';
					switch (cols[3].innerText) {
						case '<=':
						condicion.criterio = 'filtrarAntiguedadMenoroigual';
						break;
						case '<':
						condicion.criterio = 'filtrarAntiguedadMenor';
						break;
						case '>=':
						condicion.criterio = 'filtrarAntiguedadMayoroigual';
						break;
						case '>':
						condicion.criterio = 'filtrarAntiguedadMayor';
						break;
						case '<>':
						condicion.criterio = 'filtrarAntiguedadDiferente';
						break;
						case '=':
						condicion.criterio = 'filtrarAntiguedadIgual';
						break;
					}
					condicion.valor = cols[4].innerText;
				} else {
					condicion.indicador = cols[2].innerText;
					condicion.criterio = cols[3].innerText.toLowerCase();
					condicion.valor = '0';
				}
				data.condiciones[i] = condicion;
			}

			$.ajax({
				type : 'POST',
				url : "http://localhost:8080/dondeInvierto/resource/metodologias/nueva",
				dataType : "text",
				contentType : "application/json",
				data : JSON.stringify(data),
				beforeSend : function() {
					console
					.log("[INFO] (AJAX) Enviando información del indicador...");
					console.log(data);
				},
				success : function() {
					console.log("Success!");
					toggleRegistrySuccess();
				},
				error : function(jqXHR, exception) {
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
	$("#btn-calcular-ind")
	.click(
		function () {
			var data = {};
			data.empresaCalculo = $("#empresaCalculo").find("option:selected").text();
			data.periodoCalculo = $("#periodoCalculo").val();
			data.indicadorCalculo = $("#indicadorCalculo").find("option:selected").text();
			$.ajax({
				type: 'POST',
				url: "http://localhost:8080/dondeInvierto/resource/indicadores/indicadorCalculado",
				dataType: "text",
				contentType: "application/json",
				data: JSON.stringify(data),
				beforeSend: function () {
					console
					.log("[INFO] (AJAX) Enviando información del indicador...");
				},
				success: function (response) {
					$.each(response, function (index,
						element) {
						document.getElementById("valor_indicador").value = element.valor_indicador;
					});
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
	if (window.location.pathname == "/dondeInvierto/index.html") {
		$.ajax({
			type: 'GET',
			url: "http://localhost:8080/dondeInvierto/resource/login/logged",
			dataType : "json",
			beforeSend: function () {
				console
				.log("[INFO] (AJAX) Buscando información de usuario logueado...");
			},
			success: function (response) {
				if(response.usuario === "") {
					console.log("[INFO] (AJAX) No hay usuario logueado.");
				} else {
					console.log("[INFO] (AJAX) El usuario sigue logueado.");
					hideLogin();
					showNav();
				}
			}
		});
	} else {
		if (window.location.pathname == "/dondeInvierto/metodologias-registro.html") {
			$.ajax({
				type : 'GET',
				url : "http://localhost:8080/dondeInvierto/resource/indicadores/",
				dataType : "json",
				beforeSend : function() {
					console
					.log("[INFO] (AJAX) Buscando información de indicadores...");
				},
				success : function(response) {
					var selectOptionsHtml;
					$.each(response, function(index,
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
		} else {
			if (window.location.pathname == "/dondeInvierto/indicadores-calculo.html") {
				$.ajax({
					type : 'GET',
					url : "http://localhost:8080/dondeInvierto/resource/indicadores/",
					dataType : "json",
					beforeSend : function() {
						console
						.log("[INFO] (AJAX) Buscando información de indicadores...");
					},
					success : function(response) {
						var selectOptionsHtml;
						$.each(response, function(index,
							element) {
							selectOptionsHtml = selectOptionsHtml
							+ '<option>'
							+ element.nombre
							+ '</option>';
						});
						$('#indicadorCalculo')
						.html(selectOptionsHtml)
						.selectpicker('refresh');
					}
				});
				$.ajax({
					type : 'GET',
					url : "http://localhost:8080/dondeInvierto/resource/empresas/",
					dataType : "json",
					beforeSend : function() {
						console
						.log("[INFO] (AJAX) Buscando información de indicadores...");
					},
					success : function(response) {
						var selectOptionsHtml;
						$.each(response, function(index,
							element) {
							selectOptionsHtml = selectOptionsHtml
							+ '<option>'
							+ element.nombre
							+ '</option>';
						});
						$('#empresaCalculo')
						.html(selectOptionsHtml)
						.selectpicker('refresh');
					}
				});
			} else {
				if (window.location.pathname == "/dondeInvierto/configuracion.html") {
					$.ajax({
						type: 'GET',
						url: "http://localhost:8080/dondeInvierto/resource/config/getData",
						dataType: "json",
						beforeSend: function () {
							console
							.log("[INFO] (AJAX) Buscando información de configuracion...");
						},
						success: function (response) {
							$.each(response, function (index,
								element) {
								document.getElementById("path").value = element.path;
								document.getElementById("intervalo").value = element.intervalo;
							});
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
							toggleLoginError();
						}
					});
				} else {
					if (window.location.pathname == "/dondeInvierto/metodologias-calculo.html") {
						$.ajax({
							type : 'GET',
							url : "http://localhost:8080/dondeInvierto/resource/metodologias",
							dataType : "json",
							beforeSend : function() {
								console
								.log("[INFO] (AJAX) Buscando información de metodologias...");
							},
							success : function(response) {
								var selectOptionsHtml;
								$.each(response, function(index, element) {
									selectOptionsHtml = selectOptionsHtml
									+ '<option>'
									+ element.nombre
									+ '</option>';
								});
								$('#metodologia')
								.html(selectOptionsHtml)
								.selectpicker('refresh');
							}
						});
					} else {
						if (window.location.pathname == "/dondeInvierto/log_procesados.html") {
							$("#tabla-resultados").show();
							$('#tabla-archivos').replaceWith(
								$('<tbody id="tabla-archivos"></tbody>'));
							$
							.ajax({
								type: 'GET',
								url: "http://localhost:8080/dondeInvierto/resource/config/getLog",
								dataType: "json",
								beforeSend: function () {
									console
									.log("[INFO] (AJAX) Buscando información de archivos");
								},
								success: function (response) {
									$.each(response, function (index,
										element) {
										$('#tabla-archivos').append(
											$('<tr><th scope="row">'
												+ element.archivo
												+ '</td><td>'));
									});
								}
							});
						}						
					}
				}
			}
		}
	}}
	)
;



$(function btnLogin() {
	$("#btn-cerrar-sesion")
	.click(
		function () {
			$
			.ajax({
				type: 'POST',
				url: "http://localhost:8080/dondeInvierto/resource/login/out",
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

$(function () {
	$("#btn-guardar-config")
	.click(
		function () {
			var data = {};
			data.path_cuentas = $("#path").val();
			data.intervalo_cuentas = $("#intervalo").val();
			$.ajax({
				type: 'POST',
				url: "http://localhost:8080/dondeInvierto/resource/config/setData",
				dataType: "text",
				contentType: "application/json",
				data: JSON.stringify(data),
				beforeSend: function () {
					console
					.log("[INFO] (AJAX) Buscando información de configuracion ...");
				},
				success: function (response) {
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
					toggleLoginError();
				}
			});
		});
});

$(function() {
	$("#btn-calcular-meto")
	.click(
		function() {
			var metodologia = $("#metodologia").find("option:selected").text();

			if (metodologia === "Metodología a evaluar") {
				console.log("Debe seleccionar una metodología para continuar.");
			} else {
				$('#tabla-empresas').html('');
				$.ajax({
					type : 'GET',
					url : "http://localhost:8080/dondeInvierto/resource/metodologias/evaluar/" + metodologia,
					dataType : "json",
					beforeSend : function() {
						console
						.log("[INFO] (AJAX) Enviando información del indicador...");
					},
					success : function(response) {
						console.log("Success!");
						$.each(response, function(index, element) {
							$('#tabla-empresas').append(
								$('<tr><th scope="row">'
									+ (index + 1)
									+ '</th><td>'
									+ element.nombre
									+ '</td></tr>'));
						});
					}
				});
			}});
});

$(function () {
	$("#btn-visualizar_log")
	.click(
		function () {
			$
			.ajax({
				type: 'POST',
				url: "http://localhost:8080/dondeInvierto/resource/config/log",
				dataType: "text",
				contentType: "application/json",
				beforeSend: function () {
					console
					.log("[INFO] (AJAX) Buscando información de archivos procesados");
				},
				success: function (response) {
					console.log("Success!");
					document.location.href = "log_procesados.html";
					$(document).ready(hideNav());
				},
			});
		});
});

$(function () {
	$("#account-upload-btn")
	.click(
		function () {
			var data = {};
			data.nombreEmpresa = $("#nombreEmpresa").val();
			data.tipoCuenta = $("#tipoCuenta").val();
			data.periodoCuenta = $("#periodoCuenta").val();
			data.valorCuenta = $("#valorCuenta").val();
			$
			.ajax({
				type: 'POST',
				url: "http://localhost:8080/dondeInvierto/resource/cuentas/nuevaCuenta",
				dataType: "text",
				contentType: "application/json",
				data: JSON.stringify(data),
				beforeSend: function () {
					console
					.log("[INFO] (AJAX) Enviando información de cuenta");
				},
				success: function (response) {
					console.log("Success!");
					console.log(response);
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