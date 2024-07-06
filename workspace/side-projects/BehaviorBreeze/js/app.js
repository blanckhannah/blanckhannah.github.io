/* let names = [
    { firstName: "Marley" },
    { firstName: "Jevon" },
    { firstName: "Andy" },
    { firstName: "Olivia" },
    { firstName: "Eileen" },
    { firstName: "Thomas" }
];

function addStudents() {
    const nameElements = document.querySelectorAll('.name');
    nameElements.forEach((h1, index)=> {
        if (names[index]) {
            h1.innerText = `${names[index].firstName}`;
        }
    });
}
*/

let names = [];

async function addNames() {
    try {
        const response = await fetch('http://localhost:8080/students');
        const fetchedNames = await response.json();
        names.push(...fetchedNames);
    } catch(error) {
        console.error(error);
    }
}

async function getUniqueBehaviors(studentId) {
    try {
        const response = await fetch(`http://localhost:8080/behaviors/unique?id=${studentId}`);
        const behaviors = await response.json();
        return behaviors;
    } catch (error) {
        console.error(error);
        return [];
    }
}

async function createSections(names) {
    const studentsContainer = document.getElementById('students');
    await addNames();

    for (const name of names) {
        const studentSection = document.createElement('section');
        studentSection.setAttribute('class', 'student-box');
        studentSection.setAttribute('data-student-id', name.id);

        const h1 = document.createElement('h1');
        h1.setAttribute('class', 'name');
        h1.innerText = name.firstName;

        studentSection.appendChild(h1);

        const addBehaviorButton = document.createElement('button');
        addBehaviorButton.setAttribute('class', 'add-behavior-button');
        addBehaviorButton.innerText = '+';

        studentSection.appendChild(addBehaviorButton);

        await updateBehaviorDisplay(name.id, studentSection)

        studentsContainer.appendChild(studentSection);
    }
}

async function updateBehaviorDisplay(studentId, studentSection) {

        try {
            const behaviors = await getUniqueBehaviors(studentId);
            const behaviorContainer = document.createElement('div');
            behaviors.forEach((action) => {
                const behaviorDiv = document.createElement('div');
                behaviorDiv.setAttribute('class', 'behavior');

                const minusButton = document.createElement('button');
                minusButton.setAttribute('class', 'minus');
                minusButton.innerText = '-';

                const numberBehaviorDiv = document.createElement('div');
                numberBehaviorDiv.setAttribute('class', 'number-behavior');

                const count = document.createElement('h3');
                count.setAttribute('class', 'count');
                count.innerText = '0';

                const behaviorLabel = document.createElement('h2');
                behaviorLabel.setAttribute('class', 'action');
                behaviorLabel.innerText = action;

                const plusButton = document.createElement('button');
                plusButton.setAttribute('class', 'plus');
                plusButton.innerText = '+';

                const clearButton = document.createElement('button');
                clearButton.setAttribute('class', 'clear');
                clearButton.innerText = 'Clear';

                

                numberBehaviorDiv.appendChild(count);
                numberBehaviorDiv.appendChild(behaviorLabel);

                behaviorDiv.appendChild(minusButton);
                behaviorDiv.appendChild(numberBehaviorDiv);
                behaviorDiv.appendChild(plusButton);
                behaviorDiv.appendChild(clearButton);

                behaviorContainer.appendChild(behaviorDiv);
            });
            studentSection.appendChild(behaviorContainer);
        } catch (error) {
            console.error('Error fetching behaviors for ${name.firstName}:', error);
        }
    }



document.addEventListener('DOMContentLoaded', () => {
    createSections(names);


    document.getElementById('students').addEventListener('click', (event)=> {
        if (event.target.classList.contains('plus')) {
            let countElement = event.target.parentElement.querySelector('.count');
            countElement.textContent = parseInt(countElement.textContent) + 1;
        }
        if (event.target.classList.contains('minus')) {
            let countElement = event.target.parentElement.querySelector('.count');
            let currentValue = parseInt(countElement.textContent);
            if (currentValue > 0) {
                countElement.textContent = parseInt(countElement.textContent) - 1;
            }
        }
        if (event.target.classList.contains('clear')) {
            let countElement = event.target.parentElement.querySelector('.count');
            countElement.textContent = 0;
        }
        // if (event.target.classList.contains('add-behavior-button')) {
        //     window.location=('behavior.html');
        // }
        if (event.target.classList.contains('add-behavior-button')) {
            const studentSection = event.target.closest('.student-box');
            const studentId = studentSection.getAttribute('data-student-id');
            localStorage.setItem('selectedStudentId', studentId);
            window.location = ('behavior.html');
        }
    });
    
});

async function addBehaviorForStudent(studentId, action) {
    const behavior = {
        student_id: studentId,
        action: action,
        time: ''
    };
    try {
        let response = await fetch('http://localhost:8080/behaviors', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }, 
            body: JSON.stringify(behavior)
        });
        window.location = ('index.html');
    } catch (error) {
        console.error('Error adding behavior:', error);
    }
}
    
   /* document.querySelectorAll('.plus').forEach(button => {
        button.addEventListener('click', () => {
            let countElement = button.parentElement.querySelector('.count');
            countElement.textContent = parseInt(countElement.textContent) + 1;
        });
    });

    document.querySelectorAll('.minus').forEach(button => {
        button.addEventListener('click', () => {
            let countElement = button.parentElement.querySelector('.count');
            let currentValue = parseInt(countElement.textContent);
            if (currentValue > 0) {
                countElement.textContent = currentValue - 1;
            }
        });
    });

   document.querySelectorAll('.clear').forEach(button => {
        button.addEventListener('click', () => {
            let countElement = button.parentElement.querySelector('.count');
            let currentValue = parseInt(countElement.textContent);
            if (currentValue > 0) {
                countElement.textContent = 0;
            }
        })
   })
*/

/*
async function createSections(names) {
    await addNames();
    const studentsContainer = document.getElementById('students');

    names.forEach((name)=> {
        const studentSection = document.createElement('section');
        studentSection.setAttribute('class', 'student-box');

        const h1 = document.createElement('h1');
        h1.setAttribute('class', 'name');
        h1.innerText = name.firstName;

        studentSection.appendChild(h1);

        for (let i = 1; i <= 3; i++) {
            const behaviorDiv = document.createElement('div');
            behaviorDiv.setAttribute('class', 'behavior');

            const minusButton = document.createElement('button');
            minusButton.setAttribute('class', 'minus');
            minusButton.innerText = '-';

            const numberBehaviorDiv = document.createElement('div');
            numberBehaviorDiv.setAttribute('class', 'number-behavior');

            const count = document.createElement('h3');
            count.setAttribute('class', 'count');
            count.innerText = '0';

            const behaviorLabel = document.createElement('h2');
            behaviorLabel.innerText = 'Behavior';

            const plusButton = document.createElement('button');
            plusButton.setAttribute('class', 'plus');
            plusButton.innerText = '+';

            const clearButton = document.createElement('button');
            clearButton.setAttribute('class', 'clear');
            clearButton.innerText = 'Clear';

            numberBehaviorDiv.appendChild(count);
            numberBehaviorDiv.appendChild(behaviorLabel);

            behaviorDiv.appendChild(minusButton);
            behaviorDiv.appendChild(numberBehaviorDiv);
            behaviorDiv.appendChild(plusButton);
            behaviorDiv.appendChild(clearButton);

            studentSection.appendChild(behaviorDiv);
        }
        studentsContainer.appendChild(studentSection);
    });
}



document.addEventListener('DOMContentLoaded', () => {
    createSections(names);


    document.getElementById('students').addEventListener('click', (event)=> {
        if (event.target.classList.contains('plus')) {
            let countElement = event.target.parentElement.querySelector('.count');
            countElement.textContent = parseInt(countElement.textContent) + 1;
        }
        if (event.target.classList.contains('minus')) {
            let countElement = event.target.parentElement.querySelector('.count');
            countElement.textContent = parseInt(countElement.textContent) - 1;
        }
        if (event.target.classList.contains('clear')) {
            let countElement = event.target.parentElement.querySelector('.count');
            countElement.textContent = 0;
        }
    });
    
});

*/

