const btn = document.querySelector('button');
const myform = document.getElementById('form-data');
const loadingSpinner = document.getElementById('loadingSpinner'); // Get the loading spinner element

btn.addEventListener("click", (eve) => {
    eve.preventDefault();

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const email = document.getElementById('email').value.trim();
    const confirmpass = document.getElementById('confirmpass').value.trim();

    if (username === "") {
        alert("Invalid username");
        return;
    }
    if (email === "" || !/\S+@\S+\.\S+/.test(email)) {
        alert("Invalid email format");
        return;
    }
    if (password === "") {
        alert("Password cannot be empty");
        return;
    }
    if (password !== confirmpass) {
        alert("Passwords do not match");
        return;
    }

    // Show the loading spinner while the registration request is being processed
    loadingSpinner.style.display = "block";

    // Make the API call
    fetch('/user/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            userName: username,
            userPassword: password,
            userMail: email
        })
    })
    .then(response => {
        if (!response.ok) {
            // Handle HTTP errors
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        // Hide the loading spinner after the request is complete
        loadingSpinner.style.display = "none";

        alert(data.message); // Display server message
        myform.reset(); // Clear the form
    })
    .catch(error => {
        // Hide the loading spinner if there's an error
        loadingSpinner.style.display = "none";
        console.error("Error during registration:", error);
        alert("An error occurred during registration. Please try again.");
    });
});
