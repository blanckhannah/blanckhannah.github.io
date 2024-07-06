document.getElementById('create').addEventListener('click', async () => {
    const studentId = localStorage.getItem('selectedStudentId');
    const action = document.getElementById('newAction').value;

    if (studentId && action) {
        const behavior = {
            student_id: studentId,
            action: action,
            time: ''
        };
        try {
            let response = await fetch(
                `http://localhost:8080/behaviors`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                },
                body: JSON.stringify(behavior)
            });
            window.location=('index.html');
        } catch (error) {
            console.error('Error adding behavior: ', error);
        }

    } else {
        alert('Please enter an action.');
    }
});
