const urlParams = new URLSearchParams(window.location.search);
const searchFieldValue = urlParams.get('search-query');

document.getElementById('searchFieldTarget').value = searchFieldValue;


// Get the search query from the input field
var searchQuery = document.getElementById('myForm').elements['search-query'].value;

var cards = document.getElementsByClassName('col');
var noResultsFound = document.getElementById("noResultsFound");
var hasMatch = false;

if (cards.length > 0) {
    for (var i = 0; i < cards.length; i++) {
        var card = cards[i];
        var cardText = card.textContent || card.innerText;

        if (cardText.toLowerCase().includes(searchQuery.toLowerCase()) || cardText.toUpperCase().includes(searchQuery.toUpperCase())) {
            card.style.display = "block";
            hasMatch = true;
        } else {
            card.style.display = "none";
        }
    }
    if (!hasMatch) {
        noResultsFound.innerHTML = "Oops, No Search Results Found!";
    } else {
        noResultsFound.innerHTML = ""; // Clear the message if there are matching cards
    }
}
else {
    noResultsFound.innerHTML = "Oops, No Search Results Found!";
}


