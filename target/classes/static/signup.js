const btn = document.querySelector('button')

btn.addEventListener("click",(eve)=>{
    eve.preventDefault()
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;
    const confirmpass = document.getElementById('confirmpass').value;
    if (username===""){
        alert("Invalid username")
    }

    fetch('/user/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            userName: username,
            userPassword: password,
            userMail:email
        })
    })
    .then(response => response.json())
    .then(data=>{
        // const res = data.message
        // if 
        alert(data.message);
    })
    
})