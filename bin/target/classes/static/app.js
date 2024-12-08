let btn = document.querySelector('#btn');
let para = document.querySelector('#data');
let msg = "";

btn.addEventListener("click", (event) => {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('/user/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            userName: username,
            userPassword: password
        })
    })
    .then(response => response.json())
    .then(userdata => {
        const usermsg = userdata.message;
        if (usermsg === "login Successfull!") {
            para.innerText = usermsg;

            // Store username in localStorage
            localStorage.setItem('username', userdata.user.userName);
            localStorage.setItem('userId', userdata.user.userId);

            

            // Redirect to homepage after 1 second
            setTimeout(() => {
                window.location.href = "homepage.html";
            }, 2000);
        } else {
            
            366
            para.innerText = userdata.message
            console.log(userdata.message);
            
        }
        
    })
    .catch((error) => {
        para.innerText = "Internal Server Error!";
    });
}); 



// app.js
document.getElementById("togglePassword").addEventListener("click", function () {
    const passwordInput = document.getElementById("password");
    const type = passwordInput.getAttribute("type") === "password" ? "text" : "password";
    passwordInput.setAttribute("type", type);

    // Toggle icon
    this.textContent = type === "password" ? "👁️" : "🙈";
});
