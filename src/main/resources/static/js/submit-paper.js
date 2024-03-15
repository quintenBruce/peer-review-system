document.addEventListener("DOMContentLoaded", function() {
    var form = document.getElementById("paperForm");
    console.log("Form:", form); // Debugging statement

    form.addEventListener("submit", function(event) {
        event.preventDefault(); // Prevent default form submission

        var formData = new FormData(form);
        console.log("FormData:", formData); // Debugging statement

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/submit-paper", true);
        xhr.onload = function() {
            if (xhr.status === 200) {
                alert("Paper submitted successfully");
                // Redirect to another page if needed
            } else {
                alert("Failed to submit paper. Error: " + xhr.statusText);
            }
        };
        xhr.onerror = function() {
            alert("Network error occurred");
        };
        xhr.send(formData);
    });
});
