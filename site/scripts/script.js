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
    $("#uploadSuccess").fadeToggle();
}

function toggleRegistrySuccess() {
    $("#registrySuccess").fadeToggle();
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
