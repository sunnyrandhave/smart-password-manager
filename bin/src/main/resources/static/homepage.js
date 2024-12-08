
// document.addEventListener("DOMContentLoaded", () => {
//     const h1 = document.getElementById('homepage');
//     const username = localStorage.getItem('username');
//     let userId = localStorage.getItem('userId');

//      if (username!=null) {
//          h1.innerText = `Welcome, ${username}!`;
//      }

    
//      if (userId!=null) {
//          fetch(`/user/password/getByUserId/${userId}`)
//              .then((response) => {
//                  if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
//                  return response.json();
//              })
//              .then((data) => {
//                  let tabledata = data.length > 0 ?
//                      data.map((val, index) => `<tr>
//                          <th scope="row">${index + 1}</th>
//                          <td>${val.website}</td>
//                          <td>${val.websiteUsername}</td>
//                          <td>${val.websitePassword}</td>
//                        </tr>`).join('')
//                      : `<tr><td colspan="4">No passwords found for this user.</td></tr>`;

//                  document.getElementById("table-body").innerHTML = tabledata;
//              })
//              .catch((error) => {
//                  console.error("Error fetching data:", error);
//                  document.getElementById("table-body").innerHTML =
//                      `<tr><td colspan="4">Failed to load data. Please try again later.</td></tr>`;
//              });
//      } else {
//          console.error("User ID not found in localStorage.");
//      }
// });
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

        // Clear the table body
        tableBody.innerHTML = "";

        // Check if the data is empty
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

        // fetch(`/user/password/getByUserId/${userId}`)
        //     .then(response => {
        //         if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        //         return response.json();
        //     })
        //     .then(data => {
        //         const tableBody = document.getElementById("table-body");
        //         serverData = data;
        //         tableBody.innerHTML = data.length > 0 ? data.map((val, index) => `
                
        //             <tr>
        //                 <th scope="row">${index + 1}</th>
        //                 <td>${val.website}</td>
        //                 <td>${val.websiteUsername}</td>
        //                 <td>${val.websitePassword}</td>
        //                 <td>
        //                     <button class="btn btn-primary" id="editbtn" onclick="editEntry(${index})">Edit</button>
        //                      <button class="btn btn-danger" id="delbtn" onclick="deleteEntry(${index})">Delete</button>
        //                 </td>
                        
        //             </tr>`).join('') 
        //             : `<tr><td colspan="5">No passwords found for this user.</td></tr>`;
                    
                    
        //     })
        //     .catch(error => {
        //         console.error("Error fetching data:", error);
        //         document.getElementById("table-body").innerHTML =
        //             `<tr><td colspan="5">Failed to load data. Please try again later.</td></tr>`;
        //     });
    } else {
        console.error("User ID not found in localStorage.");
    }
});


// document.getElementById("delbtn").addEventListener("deleteEntry",()=>{
//     console.log("Edit Button Was Clicked");
    
// })

function editEntry(index) {
    
    
    const row = document.querySelectorAll("#table-body tr")[index];
    const cells = row.querySelectorAll("td");
    cells[0].innerHTML = `<input type="text" value="${cells[0].innerText}" />`;
    cells[1].innerHTML = `<input type="text" value="${cells[1].innerText}" />`;
    cells[2].innerHTML = `<input type="password" value="${cells[2].innerText}" />`;

    row.querySelector("button.btn-primary").textContent = "Save";
    row.querySelector("button.btn-primary").onclick = () => saveEntry(index);
    row.querySelector("button.btn-primary").onclick = () => console.log(serverData);
}

async function saveEntry(index) {
    const row = document.querySelectorAll("#table-body tr")[index];
    const inputs = row.querySelectorAll("input");
    const userId = localStorage.getItem('userId');

    const updatedData = {
        userId:serverData[index]['userId'],
        passwordId:serverData[index]['passwordId'],
        website: inputs[0].value,
        websiteUsername: inputs[1].value,
        websitePassword: inputs[2].value,
    };

    row.cells[1].innerText = updatedData.website;
    row.cells[2].innerText = updatedData.websiteUsername;
    row.cells[3].innerText = updatedData.websitePassword;

    row.querySelector("button.btn-primary").textContent = "Edit";
    row.querySelector("button.btn-primary").onclick = () => editEntry(index);

    try {
        // Optional: Send updated data to server
        const response = await fetch(`/user/password/updatePassword/${userId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updatedData),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const responseData = await response.json();
        console.log(responseData);
        
    } catch (error) {
        console.error("Failed to update password:", error);
        // Optionally, revert UI changes or show an error message
    }
}

function deleteEntry(index) {
    const passwordId = serverData[index].passwordId;
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
        console.log(`Password with ID ${passwordId} deleted successfully.`);
    })
    .catch(error => console.error("Error deleting entry:", error));
}


let btn = document.getElementById("dwn-btn");

btn.addEventListener("click",(e)=>{
    e.preventDefault();
    console.log("Button Was Clicked");
})