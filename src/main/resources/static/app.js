// This event listener runs when the page is fully loaded.
document.addEventListener('DOMContentLoaded', () => {
    populateCourseCodes();
});

// Global variables to store fetched data
let currentStudents = [];
let currentModuleCode = '';

// Available Grades for the dropdown
const grades = ["U", "G", "VG"];

// --- UTILITY FUNCTIONS ---

// Utility to display messages to the user
function showMessage(message, type = 'info') {
    const box = document.getElementById('messageBox');
    box.innerText = message;
    box.className = 'mt-4 p-3 rounded-lg text-sm'; // Reset classes
    
    if (type === 'success') {
        box.classList.add('bg-green-100', 'text-green-800');
    } else if (type === 'error') {
        box.classList.add('bg-red-100', 'text-red-800');
    } else {
        box.classList.add('bg-blue-100', 'text-blue-800');
    }
    box.classList.remove('hidden');
}

// --- API AND DATA HANDLING FUNCTIONS ---

async function populateCourseCodes() {
    const courseCodeSelect = document.getElementById('courseCode');
    try {
        const response = await fetch('/api/v1/canvas/coursecodes');
        if (!response.ok) {
            throw new Error('Could not fetch course codes.');
        }
        const courseCodes = await response.json();

        courseCodeSelect.innerHTML = '<option value="" disabled selected>Välj kurs...</option>';

        courseCodes.forEach(code => {
            const option = document.createElement('option');
            option.value = code;
            option.textContent = code;
            courseCodeSelect.appendChild(option);
        });

        courseCodeSelect.addEventListener('change', (event) => {
            const selectedCourse = event.target.value;
            if (selectedCourse) {
                populateModules(selectedCourse);
            }
        });

    } catch (error) {
        console.error('Error populating coursecodes:', error);
        courseCodeSelect.innerHTML = '<option value="" disabled selected>Kunde inte ladda kurser</option>';
    }
}

async function populateModules(courseCode) {
    const assignmentSelect = document.getElementById('assignment');
    assignmentSelect.innerHTML = '<option value="" disabled selected>Laddar uppgifter...</option>';

    try {
        const response = await fetch(`/api/v1/epokdb/modules/${courseCode}`);
        if (!response.ok) {
            throw new Error('Could not fetch modules for this course.');
        }
        const modules = await response.json();

        assignmentSelect.innerHTML = '<option value="" disabled selected>Välj uppgift...</option>'; // Clear loading text

        modules.forEach(module => {
            const option = document.createElement('option');
            option.value = module.code; // e.g., "006"
            option.textContent = `${module.code} - ${module.description}`; // e.g., "006 - projekt"
            assignmentSelect.appendChild(option);
        });

    } catch (error) {
        console.error('Error populating modules:', error);
        assignmentSelect.innerHTML = '<option value="" disabled selected>Inga uppgifter hittades</option>';
    }
}

// Fetches student and module data when the "Hämta studenter" button is clicked
async function loadData() {
    const courseCode = document.getElementById('courseCode').value;
    const moduleCode = document.getElementById('assignment').value;

    if (!courseCode || !moduleCode) {
        showMessage("Vänligen välj både kurskod och uppgift.", 'error');
        return;
    }

    showMessage(`Hämtar studenter för ${courseCode}...`, 'info');

    try {
        // 1. Get the list of enrolled usernames for the selected course/module
        const usernamesResponse = await fetch(`/api/v1/canvas/usernames/${courseCode}/${moduleCode}`);
        if (!usernamesResponse.ok) throw new Error('Could not fetch usernames.');
        const usernames = await usernamesResponse.json();

        if (usernames.length === 0) {
            showMessage("Inga studenter hittades för den valda modulen.", 'info');
            renderStudentTable([]);
            return;
        }

        // 2. Fetch ALL data for these students in ONE efficient call
        const [studentsResponse, gradesResponse] = await Promise.all([
            fetch('/api/v1/studentdb/students/by-usernames', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(usernames),
            }),
            fetch(`/api/v1/canvas/grades/${courseCode}/${moduleCode}`)
        ]);

        if (!studentsResponse.ok) throw new Error('Could not fetch student details.');
        if (!gradesResponse.ok) throw new Error('Could not fetch grades.');

        const enrolledStudents = await studentsResponse.json();
        const grades = await gradesResponse.json();
        
        currentStudents = enrolledStudents; // Store for the save function

        // 3. Render the table with all the data
        renderStudentTable(enrolledStudents, grades);
        showMessage(`Hittade ${enrolledStudents.length} studenter.`, 'success');

    } catch (error) {
        console.error("Error loading data:", error);
        showMessage(error.message, 'error');
    }
}

// Renders the student data into the HTML table (this function is now simple again)
function renderStudentTable(students, canvasGrades) {
    const tableBody = document.getElementById('studentTableBody');
    tableBody.innerHTML = ''; // Clear existing rows

    if (!students || students.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="4" class="px-6 py-4 text-center text-gray-500">Inga studenter att visa.</td></tr>`;
        return;
    }

    students.forEach(student => {
        const row = tableBody.insertRow();
        const canvasGrade = canvasGrades[student.username] || 'Ej betygsatt';

        row.insertCell().innerHTML = `<div class="px-6 py-3 whitespace-nowrap text-sm font-medium text-gray-900" title="${student.personNumber}">${student.name} (${student.username})</div>`;
        row.insertCell().innerHTML = `<div class="px-6 py-3 whitespace-nowrap text-sm text-gray-500">${canvasGrade}</div>`;

        // Ladok Grade Dropdown
        const ladokGradeCell = row.insertCell();
        const ladokGradeSelect = document.createElement('select');
        ladokGradeSelect.id = `ladok-grade-${student.username}`;
        ladokGradeSelect.className = 'p-2 border border-gray-300 rounded-lg text-sm w-full';
        grades.forEach(g => {
            const option = document.createElement('option');
            option.value = g;
            option.text = g;
            ladokGradeSelect.appendChild(option);
        });
        ladokGradeCell.appendChild(ladokGradeSelect);
        ladokGradeSelect.value = (canvasGrade !== 'Ej betygsatt') ? canvasGrade : grades[0];

        // Date Input
        const dateCell = row.insertCell();
        const dateInput = document.createElement('input');
        dateInput.type = 'date';
        dateInput.id = `date-${student.username}`;
        dateInput.className = 'p-2 border border-gray-300 rounded-lg text-sm w-full';
        dateInput.value = new Date().toISOString().split('T')[0];
        dateCell.appendChild(dateInput);
    });
}