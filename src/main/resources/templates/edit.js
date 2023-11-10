function validateForm() {
    var form = document.getElementById("myForm");
    var inputs = form.querySelectorAll("input[required]");
    var valid = true;

    inputs.forEach(function(input) {
        if (!input.checkValidity()) {
            input.classList.add("is-invalid");
            valid = false;
        } else {
            input.classList.remove("is-invalid");
        }
    });
    return valid;
}
function editItem() {
    const form = document.getElementById("myForm");
    if(validateForm()){
        myForm.addEventListener("submit", (event) => {
            event.preventDefault();
            const formData = new FormData(form);
            const searchParams = new URLSearchParams(formData);
            fetch("/saveDetails?" + `${searchParams}`, {
                method: "POST"
            })
                .then(response => {
                    if (response.ok) {
                        const popup = document.getElementById("popup");
                        popup.style.display = "block";
                    } else {
                        document.getElementById("errorPopup").style.display = "block";
                    }
                })
                .catch(error => {
                    console.error(error);
                    document.getElementById("errorPopup").style.display = "block";
                });
        });
    }
}
function confirm() {
    // Hide the popup when the user clicks the "OK" button
    const popup = document.getElementById("errorPopup");
    popup.style.display = "none";
}
function success() {
    // Hide the popup when the user clicks the "OK" button
    window.location.href = "/home";
}