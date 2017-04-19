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

function addToFormula(x) {
  $("#formula").val($("#formula").val() + x + ' ');
}
