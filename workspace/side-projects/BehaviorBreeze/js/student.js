document.getElementById('create-student').addEventListener('click', async () => {
    let firstName = document.getElementById('newFirstName').value;
    let lastName = document.getElementById('newLastName').value;

    let student = {
        firstName: firstName,
        lastName: lastName
    };

    let response = await fetch(
        `http://localhost:8080/students`, 
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(student)
        }
    );
    window.location=('index.html')
});