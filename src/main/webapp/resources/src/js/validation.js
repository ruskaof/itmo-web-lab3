export function includeValidation(

) {
    const warningElement = document.getElementById("input_y_warning");



    function hideWarning() {
        document.getElementById("input_y_warning").innerHTML = "";
        document.getElementById("input_y_warning").hidden = true;
    }

    function enableSubmitButton() {
        document.getElementById("form:submit").disabled = false;
    }

    function showLimitsWarning() {
         document.getElementById("input_y_warning").innerHTML = "Y must be a number in (-5; 3)";
         document.getElementById("input_y_warning").hidden = false;
    }

    function showLongNumberWarning() {
         document.getElementById("input_y_warning").innerHTML = "Your number is too long. We do not support long numbers :)";
         document.getElementById("input_y_warning").hidden = false;
    }

    function disableSubmitButton() {
        document.getElementById("form:submit").disabled = true;
    }

    function validateX(_event) {
        const numberPattern = /^[+-]?(\d*[.,])?\d+$/;

        const x = parseFloat(document.getElementById("form:y").value);
        if (document.getElementById("form:y").value.length > 14) {
            showLongNumberWarning()
            disableSubmitButton()
        } else if (
            Number.isNaN(x) ||
            !numberPattern.test(document.getElementById("form:y").value) ||
            x <= -5 ||
            x >= 3
        ) {
            showLimitsWarning();
            disableSubmitButton();
        } else {
            hideWarning();
            enableSubmitButton();
        }
    }

    document.getElementById("form:y").addEventListener("input", validateX);
    document.getElementById("form:y").dispatchEvent(new Event("input"));
}
