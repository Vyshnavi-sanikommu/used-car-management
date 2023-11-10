var carId;
function showPopup(id) {
    document.getElementById("popup").style.display = "block";
    carId = id;
}

function confirm(){
    document.getElementById("success-popup").style.display = "none";
    location.reload();
}

function failure(){
    document.getElementById("failure-popup").style.display = "none";
}

function cancel() {
    document.getElementById("popup").style.display = "none";
}

function deleteItem() {
    // Call the API to delete the item
    console.log("ID for calling api-->"+carId)
    fetch("/api/v1/usedcars/" + carId, {
        method: "DELETE"
    })
        .then(response => {
            if (response.ok) {
                document.getElementById("success-popup").style.display = "block";
            } else {
                document.getElementById("failure-popup").style.display = "block";
            }
        })
        .catch(error => {
            // API request failed, handle the error
            //console.error(error.message());
            document.getElementById("failure-popup").style.display = "block";
        });

    // Hide the confirmation popup
    cancel();
}