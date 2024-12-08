let serverData={};

document.addEventListener("DOMContentLoaded", () => {
    const h1 = document.getElementById('homepage');
    const username = localStorage.getItem('username');
    const userId = localStorage.getItem('userId');

    if (username) h1.innerText = `Welcome, ${username}!`;

    if (userId) {
        fetch(`/user/password/getByUserId/${userId}`)
    .then(response => {
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        return response.json();
    })
    .then(data => {
        const tableBody = document.getElementById("table-body");
        tableBody.innerHTML = "";

        if (Object.keys(data).length === 0) {
            tableBody.innerHTML = `<tr><td colspan="5">No passwords found for this user.</td></tr>`;
            return;
        }
        serverData = data;
        // Populate the table
        Object.values(data).forEach((password, index) => {
            tableBody.innerHTML += `
                <tr>
                    <th scope="row">${index + 1}</th>
                    <td>${password.website}</td>
                    <td>${password.websiteUsername}</td>
                    <td>${password.websitePassword}</td>
                    <td>
                        <button class="btn btn-primary" onclick="editEntry(${password.passwordId})">Edit</button>
                        <button class="btn btn-danger" onclick="deleteEntry(${password.passwordId})">Delete</button>
                    </td>
                </tr>`;
        });
    })
    .catch(error => console.error("Error fetching passwords:", error));

    } else {
        console.error("User ID not found in localStorage.");
    }
});





function deleteEntry(index) {
    const passwordId = serverData[index].passwordId;
    let passwordName = serverData[index].website
    // Optional: Confirmation before deletion
    if (!confirm("Are you sure you want to delete this password?")) return;

    // Remove the row from the table
    const row = document.querySelectorAll("#table-body tr")[index];
    if (row) row.remove();

    // Remove the entry from `serverData`
    serverData = Object.values(serverData).filter((_, idx) => idx !== index);

    // Send DELETE request to the server
    fetch(`/user/password/delete/${passwordId}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
    })
    .then(response => {
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        alert(`Password Deleted For ${passwordName} Succesfully!!`);
    })
    .catch(error => console.error("Error deleting entry:", error));
}


let btn = document.getElementById("dwn-btn");

btn.addEventListener("click",(e)=>{
    e.preventDefault();
    console.log("Button Was Clicked");
})