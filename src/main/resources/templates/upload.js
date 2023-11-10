const form = document.getElementById("myForm");
const submitButton = document.getElementById("submitButton");
const fileInput = document.getElementById("file");
const fileError = document.getElementById("fileError");
const successMessage = document.getElementById("successMessage");

submitButton.addEventListener("click", function(event) {
    event.preventDefault();

    const formData = new FormData();
    formData.append("file", fileInput.files[0]);
    fetch('/uploadFile', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                fileError.style.display = "none";
                successMessage.style.display = "block";
            } else {
                // API call failed, show error message
                throw new Error('API call failed');
            }
        })
        .catch(error => {
            console.error(error);
            // show error message
            fileError.style.display = "block";
        });
});