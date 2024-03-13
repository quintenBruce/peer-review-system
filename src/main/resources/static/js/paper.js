
    function submitReview() {
        var reviewText = document.getElementById('reviewText').value;

        if (reviewText.trim() !== '') {
            var url = '/papers/' + document.getElementById('reviewText').getAttribute('data-paper-id') + '/reviews';
            var formData = new FormData();
            formData.append('content', reviewText);

            var xhr = new XMLHttpRequest();
            xhr.open('POST', url, true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.send('content=' + encodeURIComponent(reviewText));

            // Optionally, you can handle the response from the server here
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 201) {
                        // Handle success for created (201) status
                        alert('Review submitted successfully!');
                        // Reload the page upon success
                        window.location.reload();
                    } else if (xhr.status === 404) {
                        // Handle not found error
                        alert('User or Paper not found!');
                    } else if (xhr.status === 500) {
                        // Handle internal server error
                        alert('Internal Server Error. Please try again later.');
                    } else {
                        // Handle other status codes
                        alert('Error submitting review! Status code: ' + xhr.status);
                    }
                }
            };



            // Optionally, reset the textarea after submission
            document.getElementById('reviewText').value = '';
        } else {
            // Handle the case where the review text is empty
            alert('Please enter a review before submitting.');
        }
    }
